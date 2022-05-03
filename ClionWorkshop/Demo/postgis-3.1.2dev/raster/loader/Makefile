#############################################################################
#
# Copyright (c) 2011 Regents of the University of California
#   <bkpark@ucdavis.edu>
# Copyright (c) 2009 Sandro Santilli <strk@kbt.io>
#
# This program is free software; you can redistribute it and/or
# modify it under the terms of the GNU General Public License
# as published by the Free Software Foundation; either version 2
# of the License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software Foundation,
# Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
#
#############################################################################

# Set CFLAGS after PGXS, otherwise it will get overwritten with the PGXS
# version which is not what we want.
CC=gcc
top_builddir = ../..
SHELL = /bin/bash
INSTALL = /usr/bin/install -c
LIBTOOL = $(SHELL) $(top_builddir)/libtool

# Filenames with extension as determined by the OS
RASTER2PGSQL=raster2pgsql

# PostgreSQL executable directory
PGSQL_BINDIR=/opt/pgsql/12.2/bin

RT_CORE=../rt_core

LIBLWGEOM_LDFLAGS= -static ../../liblwgeom/liblwgeom.la
LIBLWGEOM_CFLAGS=-I../../liblwgeom
LIBGDAL_CFLAGS=-I/usr/local/include
LIBGDAL_LDFLAGS=-L/usr/local/lib -lgdal
LIBGDAL_DEPLIBS_LDFLAGS=
PROJ_CFLAGS=-I/usr/local/include
GEOS_CFLAGS=-I/usr/local/include
GEOS_LDFLAGS=-L/usr/local/lib -lgeos_c

RTCORE_CFLAGS=-I$(RT_CORE)
RTCORE_LDFLAGS=$(RT_CORE)/librtcore.a

# GetText includes and libraries
GETTEXT_CFLAGS = 
GETTEXT_LDFLAGS =  

# iconv flags
ICONV_LDFLAGS= -lc
ICONV_CFLAGS=

CFLAGS = \
	-std=gnu99 -g -O2 -fno-math-errno -fno-signed-zeros  -fPIC -DPIC \
	$(RTCORE_CFLAGS) \
	$(LIBLWGEOM_CFLAGS) \
	$(PROJ_CFLAGS) \
	$(LIBGDAL_CFLAGS) \
	$(GEOS_CFLAGS) \
	$(GETTEXT_CFLAGS) \
	$(ICONV_CFLAGS)

LDFLAGS = \
	 -lm \
	$(LIBLWGEOM_LDFLAGS) \
	$(LIBGDAL_LDFLAGS) \
	$(LIBGDAL_DEPLIBS_LDFLAGS) \
	$(GEOS_LDFLAGS) \
	$(GETTEXT_LDFLAGS) \
	$(ICONV_LDFLAGS)

all: $(RASTER2PGSQL)

raster2pgsql.o: raster2pgsql.c
	$(CC) $(CPPFLAGS) $(CFLAGS) -c $<

$(RASTER2PGSQL): $(RT_CORE)/librtcore.a raster2pgsql.o
	$(LIBTOOL) --mode=link $(CC) $(CFLAGS) $^ $(LDFLAGS) -o $@

installdir:
	@mkdir -p $(DESTDIR)$(PGSQL_BINDIR)

install: installdir
	$(LIBTOOL) --mode=install $(INSTALL) $(RASTER2PGSQL) "$(DESTDIR)$(PGSQL_BINDIR)/$(RASTER2PGSQL)"

uninstall:
	$(LIBTOOL) --mode=uninstall $(RM) "$(DESTDIR)$(PGSQL_BINDIR)/$(RASTER2PGSQL)"

$(RT_CORE)/librtcore.a:
	$(MAKE) -C ../rt_core

clean:
	rm -rf .libs
	rm -f *.o $(RASTER2PGSQL)

distclean: clean
	rm -f Makefile
