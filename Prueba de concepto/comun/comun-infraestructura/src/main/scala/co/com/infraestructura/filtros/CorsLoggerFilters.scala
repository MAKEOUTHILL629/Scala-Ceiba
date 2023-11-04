package co.com.infraestructura.filtros

import javax.inject.{ Inject, Singleton }
import play.api.http.DefaultHttpFilters
import play.filters.cors.CORSFilter

@Singleton
class CorsLoggerFilters @Inject() (corsFilter: CORSFilter, loggingFilter: LoggingFilter) extends DefaultHttpFilters(corsFilter, loggingFilter)
