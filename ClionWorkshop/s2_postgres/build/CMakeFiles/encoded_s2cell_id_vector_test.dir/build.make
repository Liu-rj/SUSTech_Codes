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
include CMakeFiles/encoded_s2cell_id_vector_test.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/encoded_s2cell_id_vector_test.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/encoded_s2cell_id_vector_test.dir/flags.make

CMakeFiles/encoded_s2cell_id_vector_test.dir/src/s2/encoded_s2cell_id_vector_test.cc.o: CMakeFiles/encoded_s2cell_id_vector_test.dir/flags.make
CMakeFiles/encoded_s2cell_id_vector_test.dir/src/s2/encoded_s2cell_id_vector_test.cc.o: ../src/s2/encoded_s2cell_id_vector_test.cc
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/lrj/Public/s2geometry/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/encoded_s2cell_id_vector_test.dir/src/s2/encoded_s2cell_id_vector_test.cc.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/encoded_s2cell_id_vector_test.dir/src/s2/encoded_s2cell_id_vector_test.cc.o -c /home/lrj/Public/s2geometry/src/s2/encoded_s2cell_id_vector_test.cc

CMakeFiles/encoded_s2cell_id_vector_test.dir/src/s2/encoded_s2cell_id_vector_test.cc.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/encoded_s2cell_id_vector_test.dir/src/s2/encoded_s2cell_id_vector_test.cc.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/lrj/Public/s2geometry/src/s2/encoded_s2cell_id_vector_test.cc > CMakeFiles/encoded_s2cell_id_vector_test.dir/src/s2/encoded_s2cell_id_vector_test.cc.i

CMakeFiles/encoded_s2cell_id_vector_test.dir/src/s2/encoded_s2cell_id_vector_test.cc.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/encoded_s2cell_id_vector_test.dir/src/s2/encoded_s2cell_id_vector_test.cc.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/lrj/Public/s2geometry/src/s2/encoded_s2cell_id_vector_test.cc -o CMakeFiles/encoded_s2cell_id_vector_test.dir/src/s2/encoded_s2cell_id_vector_test.cc.s

# Object files for target encoded_s2cell_id_vector_test
encoded_s2cell_id_vector_test_OBJECTS = \
"CMakeFiles/encoded_s2cell_id_vector_test.dir/src/s2/encoded_s2cell_id_vector_test.cc.o"

# External object files for target encoded_s2cell_id_vector_test
encoded_s2cell_id_vector_test_EXTERNAL_OBJECTS =

encoded_s2cell_id_vector_test: CMakeFiles/encoded_s2cell_id_vector_test.dir/src/s2/encoded_s2cell_id_vector_test.cc.o
encoded_s2cell_id_vector_test: CMakeFiles/encoded_s2cell_id_vector_test.dir/build.make
encoded_s2cell_id_vector_test: libs2testing.a
encoded_s2cell_id_vector_test: libs2.so.0.10.0
encoded_s2cell_id_vector_test: lib/libgtest_main.so
encoded_s2cell_id_vector_test: /usr/lib/x86_64-linux-gnu/libgflags.so
encoded_s2cell_id_vector_test: /usr/lib/x86_64-linux-gnu/libglog.so
encoded_s2cell_id_vector_test: /usr/lib/x86_64-linux-gnu/libssl.so
encoded_s2cell_id_vector_test: /usr/lib/x86_64-linux-gnu/libcrypto.so
encoded_s2cell_id_vector_test: lib/libgtest.so
encoded_s2cell_id_vector_test: CMakeFiles/encoded_s2cell_id_vector_test.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/lrj/Public/s2geometry/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable encoded_s2cell_id_vector_test"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/encoded_s2cell_id_vector_test.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/encoded_s2cell_id_vector_test.dir/build: encoded_s2cell_id_vector_test

.PHONY : CMakeFiles/encoded_s2cell_id_vector_test.dir/build

CMakeFiles/encoded_s2cell_id_vector_test.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/encoded_s2cell_id_vector_test.dir/cmake_clean.cmake
.PHONY : CMakeFiles/encoded_s2cell_id_vector_test.dir/clean

CMakeFiles/encoded_s2cell_id_vector_test.dir/depend:
	cd /home/lrj/Public/s2geometry/build && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/lrj/Public/s2geometry /home/lrj/Public/s2geometry /home/lrj/Public/s2geometry/build /home/lrj/Public/s2geometry/build /home/lrj/Public/s2geometry/build/CMakeFiles/encoded_s2cell_id_vector_test.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/encoded_s2cell_id_vector_test.dir/depend

