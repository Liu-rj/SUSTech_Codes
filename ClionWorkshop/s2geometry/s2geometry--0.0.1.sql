-- complain if script is sourced in psql, rather than via CREATE EXTENSION
\echo Use "CREATE EXTENSION s2geometry" to load this file. \quit

-----------------------------------------------------------------------------
--  S2POINT TYPE
-----------------------------------------------------------------------------

CREATE TYPE s2point;

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

CREATE TYPE s2point
(
    internallength = 8,
    input = s2point_in,
    output = s2point_out,
    alignment = double
);

-----------------------------
-- Interfacing New Types with BTree Indexes:
-----------------------------

-- first, define the required operators
CREATE FUNCTION cell_lt(s2point, s2point) RETURNS bool
AS
'MODULE_PATHNAME' LANGUAGE C IMMUTABLE
                             STRICT;
CREATE FUNCTION cell_le(s2point, s2point) RETURNS bool
AS
'MODULE_PATHNAME' LANGUAGE C IMMUTABLE
                             STRICT;
CREATE FUNCTION cell_eq(s2point, s2point) RETURNS bool
AS
'MODULE_PATHNAME' LANGUAGE C IMMUTABLE
                             STRICT;
CREATE FUNCTION cell_ge(s2point, s2point) RETURNS bool
AS
'MODULE_PATHNAME' LANGUAGE C IMMUTABLE
                             STRICT;
CREATE FUNCTION cell_gt(s2point, s2point) RETURNS bool
AS
'MODULE_PATHNAME' LANGUAGE C IMMUTABLE
                             STRICT;

CREATE OPERATOR < (
    leftarg = s2point, rightarg = s2point, procedure = cell_lt,
    commutator = > , negator = >= ,
    restrict = scalarltsel, join = scalarltjoinsel
    );
CREATE OPERATOR <= (
    leftarg = s2point, rightarg = s2point, procedure = cell_le,
    commutator = >= , negator = > ,
    restrict = scalarlesel, join = scalarlejoinsel
    );
CREATE OPERATOR = (
    leftarg = s2point, rightarg = s2point, procedure = cell_eq,
    commutator = = ,
    -- leave out negator since we didn't create <> operator
    -- negator = <> ,
    restrict = eqsel, join = eqjoinsel
    );
CREATE OPERATOR >= (
    leftarg = s2point, rightarg = s2point, procedure = cell_ge,
    commutator = <= , negator = < ,
    restrict = scalargesel, join = scalargejoinsel
    );
CREATE OPERATOR > (
    leftarg = s2point, rightarg = s2point, procedure = cell_gt,
    commutator = < , negator = <= ,
    restrict = scalargtsel, join = scalargtjoinsel
    );

-- create the support function too
CREATE FUNCTION cell_cmp(s2point, s2point) RETURNS int4
AS
'MODULE_PATHNAME' LANGUAGE C IMMUTABLE
                             STRICT;

-- now we can make the operator class
CREATE OPERATOR CLASS s2point_ops
    DEFAULT FOR TYPE s2point USING btree AS
    OPERATOR 1 < ,
    OPERATOR 2 <= ,
    OPERATOR 3 = ,
    OPERATOR 4 >= ,
    OPERATOR 5 > ,
    FUNCTION 1 cell_cmp(s2point, s2point);

-----------------------------------------------------------------------------
--  s2cover TYPE
-----------------------------------------------------------------------------

CREATE TYPE s2cover;

-- Availability: 0.0.1
CREATE OR REPLACE FUNCTION s2cover_in(cstring, oid, integer)
    RETURNS s2cover
AS
'MODULE_PATHNAME'
    LANGUAGE C IMMUTABLE
               STRICT
               PARALLEL SAFE;

-- Availability: 0.0.1
CREATE OR REPLACE FUNCTION s2cover_out(s2cover)
    RETURNS cstring
AS
'MODULE_PATHNAME'
    LANGUAGE C IMMUTABLE
               STRICT
               PARALLEL SAFE;

CREATE TYPE s2cover
(
    internallength = variable,
    input = s2cover_in,
    output = s2cover_out,
    alignment = double
);

CREATE OR REPLACE FUNCTION proj_dist(float8)
    RETURNS float8
AS
'MODULE_PATHNAME'
    LANGUAGE C IMMUTABLE
               STRICT;

CREATE OR REPLACE FUNCTION range_cover(cell s2point, distance float8)
    RETURNS s2point
AS
'MODULE_PATHNAME'
    LANGUAGE C IMMUTABLE
               STRICT;

CREATE OR REPLACE FUNCTION s2_dwithin(cell s2point, cover s2point)
    RETURNS boolean
AS
'MODULE_PATHNAME'
    LANGUAGE C IMMUTABLE
                 STRICT
                 PARALLEL SAFE;

CREATE OR REPLACE FUNCTION s2_distance(cell1 s2point, cell2 s2point)
    RETURNS float8
AS
'MODULE_PATHNAME'
    LANGUAGE C IMMUTABLE
               STRICT
               PARALLEL SAFE;

CREATE OR REPLACE FUNCTION s2_astext(cell s2point)
    RETURNS cstring
AS
'MODULE_PATHNAME'
    LANGUAGE C IMMUTABLE
               STRICT;

CREATE OR REPLACE FUNCTION s2_range_min(cell s2point)
    RETURNS s2point
AS
'MODULE_PATHNAME'
    LANGUAGE C IMMUTABLE
               STRICT;

CREATE OR REPLACE FUNCTION s2_range_max(cell s2point)
    RETURNS s2point
AS
'MODULE_PATHNAME'
    LANGUAGE C IMMUTABLE
               STRICT;
