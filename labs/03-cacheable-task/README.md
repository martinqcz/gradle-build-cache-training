Lab 03: Making a task cacheable
===============================

This lab demonstrates how to equip a custom task with incremental and caching capabilities.

Steps
-----

1. Change to the directory of lab #3

    ```
    cd 03-cacheable-task
    ```

2. Execute the task `processText` multiple times. The task will never be `UP-TO-DATE`. Have a look at the tab 
`Performance > Task execution` in the generated build scan. The task is indicated as "not cacheable". Furthermore, click
on the `Tasks executed` link to jump to the timeline. Filter the tasks by `Outcome` by clicking on the magnifying glass
icon.

   ```
   $ gradle processText
   > Task :processText
   ```

3. Explore the timeline view of the build scan to identify why the task is not incremental. Change the code so that the 
task is marked `UP-TO-DATE` when executed at least twice. What code can be removed from the task action and why?

    ```
    $ gradle processText
    > Task :processText

    $ gradle processText
    > Task :processText UP-TO-DATE
    ```

4. Turn the task into a cacheable task. Have a look at the tab `Performance > Task execution` in the generated build scan.
What happens when you do not explicitly invoke the `clean` task and why?

    ```
    $ gradle clean processText --build-cache
    > Task :processText

    $ gradle clean processText --build-cache
    > Task :processText FROM-CACHE
    ```

5. When shared across multiple machines which portion of the build cache key could be problematic? How would line endings
of the input file factor into cacheability? Explore the tab `Performance > Build cache` in the generated build scan.

6. (Optional) Turn the custom task into a class under `buildSrc`. How do you ensure that caching is enabled for code 
under `buildSrc` and the main build? For more information see https://docs.gradle.org/current/userguide/build_cache.html#buildCacheBuildSrc.
