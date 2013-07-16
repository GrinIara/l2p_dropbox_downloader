l2p_dropbox_downloader
======================
l2p dropbox syncronizer of "Team Spirit" Team for the "eLearning course" in RWTH-Aachen

Prerequisite:
Install dropbox in your phone
Download the app from L2P.

How to use the app:
1. Open the app
2. In the initial login screen give your RWTH-Aachen userid (format: aa000000)
3. Give your password
4. You will be redirected to your L2P>My courses list. Navigate to the course content which you want to download.
5. Tap the file you want to download
6. Select your computer's dropbox folder from the dropdown list
7. You will be prompted to login to dropbox and give permission for the files. Review permission and TOS, and click yes once you agree.
8. Click on download

Technical details:
Our code is divided into 4 module:
1. Login
2. Fetch list of courses from L2P
3. Connect to dropbox api
4. Download and transfer the file from L2P to Dropbox

Know issues with version 1.0:
1. Switching between in semesters is not yet implemented. Only present semester courses will be shown.
2. It is not possible to select type of files to be downloaded. We hope to offer this feature in next version.
3. It is not possible to resume download if interrupted