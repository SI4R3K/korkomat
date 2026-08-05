package com.example.korkomat.auth.filter

import com.example.korkomat.auth.exceptions.ExpiredJwtException
import com.example.korkomat.auth.service.JwtService
import com.example.korkomat.common.constant.Constant
import com.example.korkomat.common.constant.ErrorStatus
import com.example.korkomat.common.dto.response.Api
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.util.Predicates.isExcluded
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter
import tools.jackson.databind.ObjectMapper

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val objectMapper: ObjectMapper
): OncePerRequestFilter() {

    companion object {
        private val EXCLUDED_PATHS: List<String> = mutableListOf(
            "/auth/login",
            "/auth/logout",
            "/auth/register",
            "/auth/forgot-password",
            "/auth/reset-password",
            "/auth/validate-token/**"
        )
    }

    private val pathMatcher = AntPathMatcher()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestURI = request.requestURI  // URI of the request f.e. /auth/login

        if (isExcluded(requestURI)) {
            filterChain.doFilter(request, response)
            return
        }

        try {
            val userDetails = jwtService.extractUserDetailFromRequest(request).first
            if (SecurityContextHolder.getContext().authentication == null) {
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                authentication.details = WebAuthenticationDetailsSource()
                    .buildDetails(
                        request
                    )
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (ex: ExpiredJwtException) {
            servletErrorResponseManager(Constant.JWT_EXPIRED, response)
            return
        } catch (e: MalformedJwtException) {
            servletErrorResponseManager(Constant.JWT_MALFORMED, response)
            return
        } catch (e: UsernameNotFoundException) {
            servletErrorResponseManager(Constant.USER_NOT_FOUND, response, ErrorStatus.NOT_FOUND)
            return
        } catch (e: UnsupportedJwtException) {
            servletErrorResponseManager(Constant.JWT_UNSUPPORTED, response)
            return
        } catch (e: IllegalArgumentException) {
            servletErrorResponseManager(Constant.JWT_MALFORMED, response)
            return
        }
        filterChain.doFilter(request, response)
    }

    private fun isExcluded(requestURI: String): Boolean {
        return EXCLUDED_PATHS.stream().anyMatch { path: String? ->
            pathMatcher.match(
                path!!, requestURI
            )
             }
    }

    private fun servletErrorResponseManager(
        message: String,
        response: HttpServletResponse,
        errorStatus: ErrorStatus = ErrorStatus.UNAUTHORIZED
    ) {
        val errorResponse = Api.error<Error>(
            message,
            errorStatus
        )

        val jsonResponse = objectMapper.writeValueAsString(errorResponse)
        response.contentType = "application/json"
        response.status = if (errorStatus != ErrorStatus.NOT_FOUND) HttpServletResponse.SC_UNAUTHORIZED
        else HttpServletResponse.SC_NOT_FOUND
        response.writer.write(jsonResponse)
        response.writer.flush()
    }
}