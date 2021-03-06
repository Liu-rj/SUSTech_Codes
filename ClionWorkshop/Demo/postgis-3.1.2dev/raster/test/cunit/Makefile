# **********************************************************************
# *
# * PostGIS - Spatial Types for PostgreSQL
# * http://postgis.net
# * Copyright 2008 Paul Ramsey, Mark Cave-Ayland
# *
# * This is free software; you can redistribute and/or modify it under
# * the terms of the GNU General Public Licence. See the COPYING file.
# *
# **********************************************************************

CC=gcc
top_builddir = ../../..
SHELL = /bin/bash
LIBTOOL = $(SHELL) $(top_builddir)/libtool

RT_CORE=../../rt_core

LIBLWGEOM_LDFLAGS=../../../liblwgeom/.libs/liblwgeom.a
LIBLWGEOM_CFLAGS=-I../../../liblwgeom
LIBGDAL_CFLAGS=-I/usr/local/include
LIBGDAL_LDFLAGS=-L/usr/local/lib -lgdal
PROJ_CFLAGS=-I/usr/local/include
PROJ_LDFLAGS=-L/usr/local/lib -lproj
GEOS_CFLAGS=-I/usr/local/include
GEOS_LDFLAGS=-L/usr/local/lib -lgeos_c
CUNIT_LDFLAGS=

RTCORE_CFLAGS=-I$(RT_CORE)
RTCORE_LDFLAGS=$(RT_CORE)/librtcore.a

CC=gcc
CFLAGS = \
	-I.. \
	$(LIBLWGEOM_CFLAGS) \
	$(RTCORE_CFLAGS) \
	$(PROJ_CFLAGS) \
	$(LIBGDAL_CFLAGS) \
	$(GEOS_CFLAGS) \
	 \
	-std=gnu99 -g -O2 -fno-math-errno -fno-signed-zeros

LDFLAGS = \
	$(RTCORE_LDFLAGS) \
	$(LIBLWGEOM_LDFLAGS) \
	$(LIBGDAL_LDFLAGS) \
	$(GEOS_LDFLAGS) \
	$(PROJ_LDFLAGS)


# ADD YOUR NEW TEST FILE HERE (1/1)
OBJS=	\
	cu_pixtype.o \
	cu_raster_basics.o \
	cu_band_basics.o \
	cu_raster_wkb.o \
	cu_raster_geometry.o \
	cu_raster_misc.o \
	cu_band_stats.o \
	cu_band_misc.o \
	cu_gdal.o \
	cu_spatial_relationship.o \
	cu_mapalgebra.o \
	cu_misc.o \
	cu_tester.o

# If we couldn't find the cunit library then display a helpful message
ifeq ($(CUNIT_LDFLAGS),)
all: requirements_not_met_cunit
check: requirements_not_met_cunit
else

# Build the unit tester
all: cu_tester

# Build and run the unit tester
check: cu_tester
	@./cu_tester

endif

# Build the main unit test executable
cu_tester: $(RT_CORE)/librtcore.a $(OBJS)
	$(LIBTOOL) --mode=link $(CC) $(CFLAGS) -o $@ $(OBJS) $(LDFLAGS) $(CUNIT_LDFLAGS)

# Command to build each of the .o files
$(OBJS): %.o: %.c
	$(CC) $(CFLAGS) -c -o $@ $<

$(RT_CORE)/librtcore.a:
	$(MAKE) -C ../../rt_core

# Clean target
clean:
	rm -rf .libs
	rm -f $(OBJS)
	rm -f cu_tester

distclean: clean
	rm -f Makefile

# Requirements message
requirements_not_met_cunit:
	@echo
	@echo "WARNING:"
	@echo
	@echo "configure was unable to find CUnit which is required for unit testing."
	@echo "In order to enable unit testing, you must install CUnit and then re-run configure."
	@echo
