# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.20

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:

#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:

# Disable VCS-based implicit rules.
% : %,v

# Disable VCS-based implicit rules.
% : RCS/%

# Disable VCS-based implicit rules.
% : RCS/%,v

# Disable VCS-based implicit rules.
% : SCCS/s.%

# Disable VCS-based implicit rules.
% : s.%

.SUFFIXES: .hpux_make_needs_suffix_list

# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

#Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:
.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /home/lrj/.local/share/JetBrains/Toolbox/apps/CLion/ch-0/212.5457.51/bin/cmake/linux/bin/cmake

# The command to remove a file.
RM = /home/lrj/.local/share/JetBrains/Toolbox/apps/CLion/ch-0/212.5457.51/bin/cmake/linux/bin/cmake -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/lrj/Public/s2geometry

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/lrj/Public/s2geometry/cmake-build-debug

# Include any dependencies generated for this target.
include examples/CMakeFiles/term_index.dir/depend.make
# Include the progress variables for this target.
include examples/CMakeFiles/term_index.dir/progress.make

# Include the compile flags for this target's objects.
include examples/CMakeFiles/term_index.dir/flags.make

examples/CMakeFiles/term_index.dir/term_index.cc.o: examples/CMakeFiles/term_index.dir/flags.make
examples/CMakeFiles/term_index.dir/term_index.cc.o: ../doc/examples/term_index.cc
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/lrj/Public/s2geometry/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object examples/CMakeFiles/term_index.dir/term_index.cc.o"
	cd /home/lrj/Public/s2geometry/cmake-build-debug/examples && /usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/term_index.dir/term_index.cc.o -c /home/lrj/Public/s2geometry/doc/examples/term_index.cc

examples/CMakeFiles/term_index.dir/term_index.cc.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/term_index.dir/term_index.cc.i"
	cd /home/lrj/Public/s2geometry/cmake-build-debug/examples && /usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/lrj/Public/s2geometry/doc/examples/term_index.cc > CMakeFiles/term_index.dir/term_index.cc.i

examples/CMakeFiles/term_index.dir/term_index.cc.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/term_index.dir/term_index.cc.s"
	cd /home/lrj/Public/s2geometry/cmake-build-debug/examples && /usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/lrj/Public/s2geometry/doc/examples/term_index.cc -o CMakeFiles/term_index.dir/term_index.cc.s

# Object files for target term_index
term_index_OBJECTS = \
"CMakeFiles/term_index.dir/term_index.cc.o"

# External object files for target term_index
term_index_EXTERNAL_OBJECTS =

examples/term_index: examples/CMakeFiles/term_index.dir/term_index.cc.o
examples/term_index: examples/CMakeFiles/term_index.dir/build.make
examples/term_index: libs2testing.a
examples/term_index: libs2.so.0.10.0
examples/term_index: /usr/lib/x86_64-linux-gnu/libssl.so
examples/term_index: /usr/lib/x86_64-linux-gnu/libcrypto.so
examples/term_index: examples/CMakeFiles/term_index.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/lrj/Public/s2geometry/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable term_index"
	cd /home/lrj/Public/s2geometry/cmake-build-debug/examples && $(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/term_index.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
examples/CMakeFiles/term_index.dir/build: examples/term_index
.PHONY : examples/CMakeFiles/term_index.dir/build

examples/CMakeFiles/term_index.dir/clean:
	cd /home/lrj/Public/s2geometry/cmake-build-debug/examples && $(CMAKE_COMMAND) -P CMakeFiles/term_index.dir/cmake_clean.cmake
.PHONY : examples/CMakeFiles/term_index.dir/clean

examples/CMakeFiles/term_index.dir/depend:
	cd /home/lrj/Public/s2geometry/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/lrj/Public/s2geometry /home/lrj/Public/s2geometry/doc/examples /home/lrj/Public/s2geometry/cmake-build-debug /home/lrj/Public/s2geometry/cmake-build-debug/examples /home/lrj/Public/s2geometry/cmake-build-debug/examples/CMakeFiles/term_index.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : examples/CMakeFiles/term_index.dir/depend

