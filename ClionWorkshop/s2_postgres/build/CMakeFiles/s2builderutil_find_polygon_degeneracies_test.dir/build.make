# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.16

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /usr/bin/cmake

# The command to remove a file.
RM = /usr/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/lrj/Public/s2geometry

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/lrj/Public/s2geometry/build

# Include any dependencies generated for this target.
include CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/flags.make

CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/src/s2/s2builderutil_find_polygon_degeneracies_test.cc.o: CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/flags.make
CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/src/s2/s2builderutil_find_polygon_degeneracies_test.cc.o: ../src/s2/s2builderutil_find_polygon_degeneracies_test.cc
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/lrj/Public/s2geometry/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/src/s2/s2builderutil_find_polygon_degeneracies_test.cc.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/src/s2/s2builderutil_find_polygon_degeneracies_test.cc.o -c /home/lrj/Public/s2geometry/src/s2/s2builderutil_find_polygon_degeneracies_test.cc

CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/src/s2/s2builderutil_find_polygon_degeneracies_test.cc.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/src/s2/s2builderutil_find_polygon_degeneracies_test.cc.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/lrj/Public/s2geometry/src/s2/s2builderutil_find_polygon_degeneracies_test.cc > CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/src/s2/s2builderutil_find_polygon_degeneracies_test.cc.i

CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/src/s2/s2builderutil_find_polygon_degeneracies_test.cc.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/src/s2/s2builderutil_find_polygon_degeneracies_test.cc.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/lrj/Public/s2geometry/src/s2/s2builderutil_find_polygon_degeneracies_test.cc -o CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/src/s2/s2builderutil_find_polygon_degeneracies_test.cc.s

# Object files for target s2builderutil_find_polygon_degeneracies_test
s2builderutil_find_polygon_degeneracies_test_OBJECTS = \
"CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/src/s2/s2builderutil_find_polygon_degeneracies_test.cc.o"

# External object files for target s2builderutil_find_polygon_degeneracies_test
s2builderutil_find_polygon_degeneracies_test_EXTERNAL_OBJECTS =

s2builderutil_find_polygon_degeneracies_test: CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/src/s2/s2builderutil_find_polygon_degeneracies_test.cc.o
s2builderutil_find_polygon_degeneracies_test: CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/build.make
s2builderutil_find_polygon_degeneracies_test: libs2testing.a
s2builderutil_find_polygon_degeneracies_test: libs2.so.0.10.0
s2builderutil_find_polygon_degeneracies_test: lib/libgtest_main.so
s2builderutil_find_polygon_degeneracies_test: /usr/lib/x86_64-linux-gnu/libgflags.so
s2builderutil_find_polygon_degeneracies_test: /usr/lib/x86_64-linux-gnu/libglog.so
s2builderutil_find_polygon_degeneracies_test: /usr/lib/x86_64-linux-gnu/libssl.so
s2builderutil_find_polygon_degeneracies_test: /usr/lib/x86_64-linux-gnu/libcrypto.so
s2builderutil_find_polygon_degeneracies_test: lib/libgtest.so
s2builderutil_find_polygon_degeneracies_test: CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/lrj/Public/s2geometry/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable s2builderutil_find_polygon_degeneracies_test"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/build: s2builderutil_find_polygon_degeneracies_test

.PHONY : CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/build

CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/cmake_clean.cmake
.PHONY : CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/clean

CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/depend:
	cd /home/lrj/Public/s2geometry/build && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/lrj/Public/s2geometry /home/lrj/Public/s2geometry /home/lrj/Public/s2geometry/build /home/lrj/Public/s2geometry/build /home/lrj/Public/s2geometry/build/CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/s2builderutil_find_polygon_degeneracies_test.dir/depend

