# Testing

Our current testing strategy is split into two parts:

* [Unit Testing](#unit-testing)
* [Manual Acceptance Testing](#manual-acceptance-testing)

## Unit Testing
### Prerequisite
In order to use the `lein test` and `lein test-auto` aliases, it's required that [phantomjs](http://phantomjs.org/) is installed and available on the PATH. Other environments are available (e.g. slimer, rhino etc) find out how to install and run them [here](https://github.com/bensu/doo#setting-up-environments).

### Running
There are three ways to run the unit tests:
* `lein test` will run the tests once and report a result.
* `lein test-auto` will run the tests and re-run the tests if it detects any source files to have changed.
* Build the Docker test environment from the project directory with `docker build -t 'witan-ui-test' -f scripts/test.dockerfile .` , and then `docker run --rm witan-ui-test lein test` to run the tests once, and report a result.  

### Writing
Additional tests should be written inside the `test/cljs/witan/ui/test` directory and the appropriate namespace should be added to `test/cljs/witan/ui/runner.cljs`. The test macros are provided by `cljs.test` and executed by `lein-doo`.

## Manual Acceptance Testing
Until there is an automated way to run acceptance tests the only way to catch regressions is with a manual script/checklist:

*Last updated 21/06/17*

| Test  | Expected Result |
| --------| ------ |
| Can the user reset their password? | The user receives a password reset email containing a link to the password reset form. |
| Can the user login? | The user can enter their password and be taken to the dashboard. |
| Can the user upload a file without sharing? | The user can upload any file (<100mb) from the Upload screen without opting to share it. |
| Can the user upload a file with sharing? | The user can upload any file (<100mb) from the Upload screen, opting to share it with another group. |
| Can the user navigate to a recently uploaded file? | From the dashboard, the user can view files uploaded in the last couple of minutes. |
| Can the user download a file? | The user can see and click the 'Download' button on a file and the file download occurs.|
| Can the user edit metadata on a file they have permissions on? | The user can edit metadata, hit Save, reload the file and observe that the edit has persisted.|
| Can the user edit sharing details on a file they have permissions on? | The user can adjust the sharing details, reload the file and observe that the edit has persisted.|
| Is the user prevented from editing metadata on a file for which they don't have permission? | The user is not presented with any options to edit the metadata and cannot navigate to the 'Edit' view.|
| Is the user prevented from editing sharing details on a file for which they don't have permission? | The user is not presented with any options to adjust the sharing details and cannot use the search facility of the 'Sharing' view.|
| Does loading a file take < 1s? | When navigating from the dashboard to a file, the loading icon spins for no more than 1s. | Does saving metadata take < 3.5s? | When hitting 'Save' after editing metadata, the button should stay disabled for no more than 3.5s. |