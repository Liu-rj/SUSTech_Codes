# generated automatically by aclocal 1.16.3 -*- Autoconf -*-

# Copyright (C) 1996-2020 Free Software Foundation, Inc.

# This file is free software; the Free Software Foundation
# gives unlimited permission to copy and/or distribute it,
# with or without modifications, as long as this notice is preserved.

# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY, to the extent permitted by law; without
# even the implied warranty of MERCHANTABILITY or FITNESS FOR A
# PARTICULAR PURPOSE.

m4_ifndef([AC_CONFIG_MACRO_DIRS], [m4_defun([_AM_CONFIG_MACRO_DIRS], [])m4_defun([AC_CONFIG_MACRO_DIRS], [_AM_CONFIG_MACRO_DIRS($@)])])
# Copyright (C) 2003-2020 Free Software Foundation, Inc.
#
# This file is free software; the Free Software Foundation
# gives unlimited permission to copy and/or distribute it,
# with or without modifications, as long as this notice is preserved.

# AM_PROG_MKDIR_P
# ---------------
# Check for 'mkdir -p'.
AC_DEFUN([AM_PROG_MKDIR_P],
[AC_PREREQ([2.60])dnl
AC_REQUIRE([AC_PROG_MKDIR_P])dnl
dnl FIXME we are no longer going to remove this! adjust warning
dnl FIXME message accordingly.
AC_DIAGNOSE([obsolete],
[$0: this macro is deprecated, and will soon be removed.
You should use the Autoconf-provided 'AC][_PROG_MKDIR_P' macro instead,
and use '$(MKDIR_P)' instead of '$(mkdir_p)'in your Makefile.am files.])
dnl Automake 1.8 to 1.9.6 used to define mkdir_p.  We now use MKDIR_P,
dnl while keeping a definition of mkdir_p for backward compatibility.
dnl @MKDIR_P@ is magic: AC_OUTPUT adjusts its value for each Makefile.
dnl However we cannot define mkdir_p as $(MKDIR_P) for the sake of
dnl Makefile.ins that do not define MKDIR_P, so we do our own
dnl adjustment using top_builddir (which is defined more often than
dnl MKDIR_P).
AC_SUBST([mkdir_p], ["$MKDIR_P"])dnl
case $mkdir_p in
  [[\\/$]]* | ?:[[\\/]]*) ;;
  */*) mkdir_p="\$(top_builddir)/$mkdir_p" ;;
esac
])

m4_include([macros/ac_proj4_version.m4])
m4_include([macros/ac_protobufc_version.m4])
m4_include([macros/ax_cxx_compile_stdcxx.m4])
m4_include([macros/gettext.m4])
m4_include([macros/gtk-2.0.m4])
m4_include([macros/iconv.m4])
m4_include([macros/intlmacosx.m4])
m4_include([macros/lib-ld.m4])
m4_include([macros/lib-link.m4])
m4_include([macros/lib-prefix.m4])
m4_include([macros/libtool.m4])
m4_include([macros/ltoptions.m4])
m4_include([macros/ltsugar.m4])
m4_include([macros/ltversion.m4])
m4_include([macros/lt~obsolete.m4])
m4_include([macros/nls.m4])
m4_include([macros/pkg.m4])
m4_include([macros/po.m4])
m4_include([macros/progtest.m4])
