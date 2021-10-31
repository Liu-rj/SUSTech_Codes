-- complain if script is sourced in psql, rather than via CREATE EXTENSION
\echo Use "CREATE EXTENSION s2geometry" to load this file. \quit

CREATE OR REPLACE FUNCTION RadiansToPoint(numeric, numeric)
    RETURNS boolean
--     AS 'MODULE_PATHNAME','s2latlng'
    AS '$libdir/test'
    LANGUAGE C IMMUTABLE CALLED ON NULL INPUT;
