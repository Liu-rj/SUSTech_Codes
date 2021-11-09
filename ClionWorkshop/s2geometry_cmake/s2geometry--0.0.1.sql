-- complain if script is sourced in psql, rather than via CREATE EXTENSION
\echo Use "CREATE EXTENSION s2geometry" to load this file. \quit

CREATE OR REPLACE FUNCTION test_equal(integer, integer)
    RETURNS boolean
    AS '$libdir/s2geometry'
    LANGUAGE C IMMUTABLE STRICT;
