










-- PostGIS - Spatial Types for PostgreSQL
-- http://postgis.net
--
-- Copyright (C) 2012 Regina Obe <lr@pcorp.us>
-- This is free software; you can redistribute and/or modify it under
-- the terms of the GNU General Public Licence. See the COPYING file.
--
--
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
-- These are reserved for functions where the they are changed to use default args
-- This is installed after the new functions are installed
-- We don't have any of these yet for topology
-- The reason we put these after install is
-- you can't drop a function that is used by sql functions
-- without forcing a drop on those as well which may cause issues with user functions.
-- This allows us to CREATE OR REPLACE those in general topology.sql
-- without dropping them.

