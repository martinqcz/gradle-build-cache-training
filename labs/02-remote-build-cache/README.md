Lab 02: Using the remote build cache
====================================

This lab demonstrates using Gradle Enterprise as a remote build cache. A build has been provided that is already configured 
with the remote build cache configuration. The local cache has been disabled for demonstration purposes. You can see the
 configuration in `settings.gradle`.

Steps
-----

1. Change to the directory of lab #2

    ```
    cd 02-remote-build-cache
    ```

2. Run a build. This build will execute without the cache being populated.

   ```
   $ gradle clean check --build-cache
   > Task :clean
   > Task :compileJava
   > Task :processResources NO-SOURCE
   > Task :classes
   > Task :compileTestJava
   > Task :processTestResources NO-SOURCE
   > Task :testClasses
   > Task :test
   > Task :check
   
   BUILD SUCCESSFUL in 1s
   4 actionable tasks: 4 executed
   ```

3. Run the same build again. This time the remote build cache has been populated from the last run.

   ```
   $ gradle clean check --build-cache
   > Task :clean
   > Task :compileJava FROM-CACHE
   > Task :processResources NO-SOURCE
   > Task :classes UP-TO-DATE
   > Task :compileTestJava FROM-CACHE
   > Task :processTestResources NO-SOURCE
   > Task :testClasses UP-TO-DATE
   > Task :test FROM-CACHE
   > Task :check UP-TO-DATE
   
   
   BUILD SUCCESSFUL in 1s
   4 actionable tasks: 1 executed, 3 from cache
   ```

4. View the build cache usage summary page to see the global hit rate at https://enterprise-training.gradle.com/cache-admin.

5. Finally, run the same build again, refresh the build cache usage summary page and see how the hits increased.

6. Open the build scan URL in the browser and inspect aspects like download speeds, unpack times, etc. under 
`Performance > Task execution`, `Performance > Build cache` and `Performance > Settings and suggestions`.
