-- complain if script is sourced in psql, rather than via CREATE EXTENSION
\echo Use "CREATE EXTENSION proj_dist" to load this file. \quit
CREATE OR REPLACE FUNCTION proj_dist(float8)
    RETURNS float8
    AS '$libdir/proj_dist'
    LANGUAGE C IMMUTABLE STRICT;
