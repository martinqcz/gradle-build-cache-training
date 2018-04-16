Lab 01: Using the local build cache
===================================

This lab demonstrates using the local build cache. The sample project demonstrates the difference between using the 
new task output caching vs. relying only on the existing incremental builds feature.

Steps
-----

1. Change to the directory of lab #1.

    ```
    $ cd 01-local-build-cache
    ```

2. Run the application without caching. On the first run all task actions will execute that have sources.

    ```
    $ gradle run
    > Task :compileJava
    > Task :processResources NO-SOURCE
    > Task :classes
    > Task :run
    Hello World!
    ```

3. Run again without changes. Incremental build functionality will mark tasks `UP-TO-DATE`.

    ```
    $ gradle run
    > Task :compileJava UP-TO-DATE
    > Task :processResources NO-SOURCE
    > Task :classes UP-TO-DATE
    > Task :run
    Hello World!
    ```

4. Remove the workspace's output and run again.

    ```
    $ gradle clean run
    > Task :clean
    > Task :compileJava
    > Task :processResources NO-SOURCE
    > Task :classes
    > Task :run
    Hello World!
    ```

5. Ensure local cache doesn't exist. The following example shows the removal of the cache created by Gradle 4.6.

    ```
    $ rm -rf ~/.gradle/caches/build-cache-1
    ```

6. Run the application with the build cache enabled. Also remove the workspace's output and run again.

    ```
    $ gradle clean run --build-cache
    > Task :compileJava
    > Task :processResources NO-SOURCE
    > Task :classes
    > Task :run
    Hello World!
    ```

7. Run the application again (even with removing the incremental build state). Existing build results are pulled from 
the local cache indicated by `FROM-CACHE`.

    ```
    $ gradle clean run --build-cache
    > Task :clean
    > Task :compileJava FROM-CACHE
    > Task :processResources NO-SOURCE
    > Task :classes UP-TO-DATE
    > Task :run
    Hello World!
    ```

8. Configure the local cache to point to host the cache files in a non-standard directory. Set the number of days 
after unused entries are garbage collected to 30 days. Find the custom local cache directory on disk after its creation.

_settings.gradle_

    ```
    buildCache {
        local(DirectoryBuildCache) {
            directory = new File(rootDir, 'build-cache')
            removeUnusedEntriesAfterDays = 30
        }
    }
    ```
