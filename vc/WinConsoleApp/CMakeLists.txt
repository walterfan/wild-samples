# CMakeList.txt : CMake project for WinConsoleApp, include source and define
# project specific logic here.
#
cmake_minimum_required (VERSION 3.8)

project ("WinConsoleApp")

include_directories(abseil-cpp)

add_subdirectory(abseil-cpp)

# Add source to this project's executable.
add_executable (WinConsoleApp "WinConsoleApp.cpp" "WinConsoleApp.h" "WebrtcSnippet.h")

# TODO: Add tests and install targets if needed.
