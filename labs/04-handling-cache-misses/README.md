Lab 04: Handling cache misses
=============================

This lab demonstrates how to deal with cache misses. The sample code represents a multi-project build. The project 
`build-info` generates a file `build-info.properties` containing the current timestamp and the project version. The
project `application` uses the artifact produced by `build-info` for compiling the code.

Steps
-----

1. Change to the directory of lab #4

    ```
    cd 04-handling-cache-misses
    ```

2. Execute the task `:application:test` at least twice. Identify the reason why the task was not marked `UP-TO-DATE`
with the help of a timeline feature of the build scan. Furthermore, identify the reasons why the tasks `:build-info:jar`
and `:build-info:buildInfo` were not marked `UP-TO-DATE`.

    ```
    $ gradle application:test
    > Task :application:processResources NO-SOURCE
    > Task :application:processTestResources NO-SOURCE
    > Task :build-info:compileJava UP-TO-DATE
    > Task :build-info:buildInfo
    > Task :build-info:processResources NO-SOURCE
    > Task :build-info:classes
    > Task :application:compileJava UP-TO-DATE
    > Task :application:classes UP-TO-DATE
    > Task :build-info:jar
    > Task :application:compileTestJava UP-TO-DATE
    > Task :application:testClasses UP-TO-DATE
    > Task :application:test
    ```

3. Explore the build comparison feature in the timeline view by clicking the icon in the top right hand corner with the
tooltip "Navigate to earlier/later builds run in the same workspace". Compare the current build with the previous build
and identify the task differences.

4. Normalize the runtime classpath of the `application` project to ignore volatile input files. Execute the task 
`:application:test` at least twice. Verify that the task is now `UP-TO-DATE` with the help of a build scan. Furthermore,
check what has changed in the build comparison view.

    ```
    $ gradle application:test
    > Task :application:processResources NO-SOURCE
    > Task :application:processTestResources NO-SOURCE
    > Task :build-info:compileJava UP-TO-DATE
    > Task :build-info:buildInfo
    > Task :build-info:processResources NO-SOURCE
    > Task :build-info:classes
    > Task :application:compileJava UP-TO-DATE
    > Task :application:classes UP-TO-DATE
    > Task :build-info:jar
    > Task :application:compileTestJava UP-TO-DATE
    > Task :application:testClasses UP-TO-DATE
    > Task :application:test UP-TO-DATE
    ```

5. Clean the existing workspace by running the `clean` task. Execute the task with the build cache enabled with 
`:application:test --build-cache`.

    ```
    $ gradle application:test --build-cache
    > Task :application:processResources NO-SOURCE
    > Task :application:processTestResources NO-SOURCE
    > Task :build-info:compileJava FROM-CACHE
    > Task :build-info:buildInfo
    > Task :build-info:processResources NO-SOURCE
    > Task :build-info:classes
    > Task :build-info:jar
    > Task :application:compileJava FROM-CACHE
    > Task :application:classes UP-TO-DATE
    > Task :application:compileTestJava FROM-CACHE
    > Task :application:testClasses UP-TO-DATE
    > Task :application:test FROM-CACHE
    ```

6. Change one of the inputs to the `:application:test` task (e.g. the content of the files under 
`src/integration-test-samples`) and run `clean :application:test --build-cache` again. Why was the test cached even
though it should have failed?

    ```
    $ gradle clean application:test --build-cache
    > Task :clean UP-TO-DATE
    > Task :build-info:clean
    > Task :application:clean
    > Task :application:processResources NO-SOURCE
    > Task :application:processTestResources NO-SOURCE
    > Task :build-info:compileJava FROM-CACHE
    > Task :build-info:buildInfo
    > Task :build-info:processResources NO-SOURCE
    > Task :build-info:classes
    > Task :build-info:jar
    > Task :application:compileJava FROM-CACHE
    > Task :application:classes UP-TO-DATE
    > Task :application:compileTestJava FROM-CACHE
    > Task :application:testClasses UP-TO-DATE
    > Task :application:test FROM-CACHE
    ```

7. Add the directory `src/integration-test-samples` as input directory to the test task. The test task should fail when
run with `clean :application:test --build-cache`.

    ```
    $ gradle clean application:test --build-cache
    > Task :clean UP-TO-DATE
    > Task :build-info:clean
    > Task :application:clean
    > Task :application:processResources NO-SOURCE
    > Task :application:processTestResources NO-SOURCE
    > Task :build-info:compileJava FROM-CACHE
    > Task :build-info:buildInfo
    > Task :build-info:processResources NO-SOURCE
    > Task :build-info:classes
    > Task :build-info:jar
    > Task :application:compileJava FROM-CACHE
    > Task :application:classes UP-TO-DATE
    > Task :application:compileTestJava FROM-CACHE
    > Task :application:testClasses UP-TO-DATE
    > Task :application:test FAILED
    
    com.example.application.ApplicationStartupTest > applicationShouldStartup FAILED
        java.lang.AssertionError at ApplicationStartupTest.java:27
    
    1 test completed, 1 failed
    ```

8. Fix the content of the files to reflect the assertions in the test class. Run `clean :application:test --build-cache`
multiple times. The task should be loaded from the cache.

    ```
    $ gradle clean application:test --build-cache
    > Task :clean UP-TO-DATE
    > Task :build-info:clean
    > Task :application:clean
    > Task :application:processResources NO-SOURCE
    > Task :application:processTestResources NO-SOURCE
    > Task :build-info:compileJava FROM-CACHE
    > Task :build-info:buildInfo
    > Task :build-info:processResources NO-SOURCE
    > Task :build-info:classes
    > Task :build-info:jar
    > Task :application:compileJava FROM-CACHE
    > Task :application:classes UP-TO-DATE
    > Task :application:compileTestJava FROM-CACHE
    > Task :application:testClasses UP-TO-DATE
    > Task :application:test FROM-CACHE
    ```

9. Discuss if the same project checked out into a different directory on another machine would expose the same cache
behavior. Change the code so that it that it would be guaranteed to work in other workspaces.
