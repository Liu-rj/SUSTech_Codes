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

top_builddir = ../..
CC=gcc
CFLAGS=-std=gnu99 -g -O2 -fno-math-errno -fno-signed-zeros
SHELL = /bin/bash
LIBTOOL = $(SHELL) $(top_builddir)/libtool

CUNIT_LDFLAGS=
CUNIT_CPPFLAGS=-I..   -I/usr/local/include -I/usr/local/include  -I/usr/local/include/libxml2  -I/usr/local/include/json-c  -DNDEBUG 

# GTK includes and libraries
GTK_CFLAGS =  
GTK_LIBS =  
GTK_WIN32_FLAGS = 

# PostgreSQL frontend CPPFLAGS and LDFLAGS (for compiling and linking with libpq)
PGSQL_FE_CPPFLAGS=-I/opt/pgsql/12.2/include
PGSQL_FE_LDFLAGS=-L/opt/pgsql/12.2/lib -lpq

# liblwgeom
LIBLWGEOM=../../liblwgeom/.libs/liblwgeom.la

# iconv flags
ICONV_LDFLAGS= -lc
ICONV_CFLAGS=

# GetText includes and libraries
GETTEXT_CFLAGS = 
GETTEXT_LDFLAGS =  

# Proj flags
PROJ_CFLAGS=-I/usr/local/include
PROJ_LDFLAGS=-L/usr/local/lib -lproj

# Built out CFLAGS with ICONV and GETTEXT
CFLAGS += $(GETTEXT_CFLAGS) $(ICONV_CFLAGS) $(PROJ_CFLAGS)

# Build full linking line
LDFLAGS = $(GEOS_LDFLAGS) $(GETTEXT_LDFLAGS) $(PGSQL_FE_LDFLAGS) $(ICONV_LDFLAGS) $(CUNIT_LDFLAGS) $(PROJ_LDFLAGS)

# Object files
OBJS=	\
	cu_pgsql2shp.o \
	cu_shp2pgsql.o \
	cu_tester.o

LOADER_OBJS= \
	../dbfopen.o \
	../shpopen.o \
	../getopt.o \
	../shpcommon.o \
	../safileio.o \
	../pgsql2shp-core.o \
	../shp2pgsql-core.o

# If we couldn't find the cunit library then display a helpful message
ifeq ($(CUNIT_LDFLAGS),)
all: requirements_not_met_cunit
check: requirements_not_met_cunit
else

$(LOADER_OBJS):
	$(MAKE) -C ..

# Build the unit tester
all: cu_tester

# Build and run the unit tester
check: cu_tester
	@./cu_tester

endif

# Build the main unit test executable
cu_tester: $(LOADER_OBJS) $(OBJS)
	$(LIBTOOL) --mode=link \
	$(CC) $(CFLAGS) $^ -o $@ $(LIBLWGEOM) $(LDFLAGS)

# Command to build each of the .o files
$(OBJS): %.o: %.c
	$(CC) $(CFLAGS) $(CUNIT_CPPFLAGS) $(GTK_CFLAGS) $(PGSQL_FE_CPPFLAGS) -c -o $@ $<

# Clean target
clean:
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
