-- complain if script is sourced in psql, rather than via CREATE EXTENSION
\echo Use "CREATE EXTENSION test" to load this file. \quit
CREATE FUNCTION test(integer, integer)
    RETURNS boolean
    AS '$libdir/test'
    LANGUAGE C IMMUTABLE STRICT;
