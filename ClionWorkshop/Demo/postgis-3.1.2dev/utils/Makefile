# **********************************************************************
# *
# * PostGIS - Spatial Types for PostgreSQL
# * http://postgis.net
# *
# * Copyright 2008 Mark Cave-Ayland
# *
# * This is free software; you can redistribute and/or modify it under
# * the terms of the GNU General Public Licence. See the COPYING file.
# *
# **********************************************************************

# Separate PGXS-enabled Makefile for documentation installation (it is
# not possible to merge into the main Makefile as has been done for
# the shapefile loader)

MODULE_doc=postgis-3.1
MODULEDIR=contrib/$(MODULE_doc)

# Files to be copied to the contrib/ directory
DATA_built=postgis_restore.pl

# PGXS information
PG_CONFIG := /opt/pgsql/12.2/bin/pg_config
PGXS := /opt/pgsql/12.2/lib/pgxs/src/makefiles/pgxs.mk
include $(PGXS)

SHELL = /bin/bash
INSTALL = $(SHELL) ../build-aux/install-sh

SCRIPTS_built = postgis_restore.pl

SCRIPTS = \
	create_undef.pl \
	repo_revision.pl \
	postgis_proc_upgrade.pl \
	profile_intersects.pl \
	test_estimation.pl \
	test_joinestimation.pl

SRID_MAXIMUM = 999999
SRID_USER_MAXIMUM = 998999

all: postgis_restore.pl
	chmod +x $(SCRIPTS)

DROP_FILES = \
  ../raster/rt_pg/rtpostgis_upgrade_cleanup.sql.in \
  ../raster/rt_pg/rtpostgis_drop.sql.in \
  ../postgis/postgis_after_upgrade.sql \
  ../postgis/postgis_before_upgrade.sql

update-restore:
	tac postgis_restore.pl.in | sed '/^__END__/q' | grep -v '^__END__' \
    > postgis_restore_data
	grep '^DROP FUNCTION IF EXISTS' $(DROP_FILES) | \
    cut -d: -f2 | sed 's/^DROP FUNCTION IF EXISTS //' | \
    sed 's/ *, */,/g' | \
    sed 's/ *( */(/g' | \
    sed 's/ *) */)/g' | \
    tr '[A-Z]' '[a-z]' | \
    sed 's/varchar/character varying/g' | \
    sed 's/float8/double precision/g' | \
    sed 's/\<int\>/integer/g' | \
    sed 's/\<int4\>/integer/g' | \
    sed 's/\<int8\>/bigint/g' | \
    sed 's/\(.*\); *\(--.*\)\?/FUNCTION \1/' >> postgis_restore_data
	cat postgis_restore.pl.in | sed '/^__END__/q' > postgis_restore.pl.in.new
	cat postgis_restore_data | sort -u >> postgis_restore.pl.in.new
	mv postgis_restore.pl.in.new postgis_restore.pl.in
	rm -f postgis_restore_data

postgis_restore.pl: postgis_restore.pl.in
	sed 's,@SRID_MAXIMUM@,$(SRID_MAXIMUM),g;s,@SRID_USER_MAXIMUM@,$(SRID_USER_MAXIMUM),' $< >$@

distclean: clean
	rm -f Makefile

# And there's nothing to check
check check-unit check-regress:
