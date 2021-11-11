-- complain if script is sourced in psql, rather than via CREATE EXTENSION
\echo Use "CREATE EXTENSION s2geometry" to load this file. \quit

-----------------------------------------------------------------------------
--  S2POINT TYPE
-----------------------------------------------------------------------------

-- Availability: 0.0.1
CREATE OR REPLACE FUNCTION s2point_in(cstring, oid, integer)
    RETURNS s2point
AS
'MODULE_PATHNAME'
    LANGUAGE C IMMUTABLE
               STRICT
               PARALLEL SAFE;

-- Availability: 0.0.1
CREATE OR REPLACE FUNCTION s2point_out(s2point)
    RETURNS cstring
AS
'MODULE_PATHNAME'
    LANGUAGE C IMMUTABLE
               STRICT
               PARALLEL SAFE;

-- -- Availability: 0.0.1
-- CREATE OR REPLACE FUNCTION s2point_typmod_in(cstring[])
--     RETURNS integer
-- AS '$libdir/postgis-3','s2point_typmod_in'
--     LANGUAGE 'c' IMMUTABLE STRICT PARALLEL SAFE;
--
-- -- Availability: 0.0.1
-- CREATE OR REPLACE FUNCTION s2point_typmod_out(integer)
--     RETURNS cstring
-- AS '$libdir/postgis-3','s2point_typmod_out'
--     LANGUAGE 'c' IMMUTABLE STRICT PARALLEL SAFE;
--
-- -- Availability: 0.0.1
-- CREATE OR REPLACE FUNCTION s2point_recv(internal, oid, integer)
--     RETURNS s2point
-- AS '$libdir/postgis-3','s2point_recv'
--     LANGUAGE 'c' IMMUTABLE STRICT PARALLEL SAFE;
--
-- -- Availability: 0.0.1
-- CREATE OR REPLACE FUNCTION s2point_send(geography)
--     RETURNS bytea
-- AS '$libdir/postgis-3','s2point_send'
--     LANGUAGE 'c' IMMUTABLE STRICT PARALLEL SAFE;
--
-- -- Availability: 0.0.1
-- CREATE OR REPLACE FUNCTION s2point_analyze(internal)
--     RETURNS bool
-- AS '$libdir/postgis-3','s2point_analyze'
--     LANGUAGE 'c' VOLATILE STRICT;

CREATE TYPE s2point
(
    internallength = variable,
    input = s2point_in,
    output = s2point_out, --     receive = s2point_recv,
--     send = s2point_send,
--     typmod_in = s2point_typmod_in,
--     typmod_out = s2point_typmod_out,
    delimiter = ',',
--     analyze = s2point_analyze,
    storage = main,
    alignment = double
);

CREATE OR REPLACE FUNCTION test_equal(integer, integer)
    RETURNS boolean
AS
'MODULE_PATHNAME'
    LANGUAGE C IMMUTABLE
               STRICT
               PARALLEL SAFE;
