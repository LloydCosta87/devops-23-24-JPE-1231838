
# Class Assignment 1

## Introduction

This report outlines the process of managing a software project using Git, detailing the creation of a GitHub repository, initial setup, and the development of new features following a structured version control system.

## Initial Setup

To start, create a new repository on GitHub and initialize it locally:

1. **Create README.md and Initialize Repository:**
   ```bash
   echo "# Teste" >> README.md
   ```
   This command creates a `README.md` file and adds a title to it.

2. **Initialize Git Repository:**
   ```bash
   git init
   ```
   Initializes a new Git repository locally, creating a `.git` directory.

3. **Stage and Commit the README.md:**
   ```bash
   git add README.md
   git commit -m "first commit"
   ```
   Stages the `README.md` file and commits it with a message.

4. **Set Main as Default Branch:**
   ```bash
   git branch -M main
   ```
   Renames the default branch to `main`.

5. **Add Remote Repository:**
   ```bash
   git remote add origin git@github.com:LloydCosta87/Teste.git
   ```
   Links your local repository with the remote GitHub repository.

6. **Push Changes to GitHub:**
   ```bash
   git push -u origin main
   ```
   Pushes the commit to GitHub, setting `main` as the upstream branch.

## Task 1: Managing the Master Branch

Using only the `master` branch, the following steps were undertaken:

1. **Copy Tutorial Code:**
   ```bash
   cp -r path/to/TutorialReactSpringDataREST ./CA1
   ```
   Copies the tutorial project into a new folder named `CA1`.

2. **Commit and Push Initial Code:**
   ```bash
   git add .
   git commit -m "Initial project setup"
   git push origin main
   ```
   Adds all files to staging, commits them to the repository, and pushes to the `main` branch on GitHub.

3. **Tagging Initial Version:**
   ```bash
   git tag -a v1.1.0 -m "Initial version"
   git push origin v1.1.0
   ```
   Tags the initial version of the application and pushes the tag to GitHub.

4. **Develop New Feature and Commit:**
   After developing a new feature (e.g., adding a `jobYears` field), and testing it thoroughly:
   ```bash
   git add .
   git commit -m "Added jobYears field"
   git push origin main
   ```
   Stages the changes, commits them with a descriptive message, and pushes to the main branch.

5. **Tagging Feature Release:**
   ```bash
   git tag -a v1.2.0 -m "Added new feature: jobYears"
   git push origin v1.2.0
   ```
   Tags the new feature release and pushes the tag to GitHub.

6. **Marking Assignment Completion:**
   ```bash
   git tag -a ca1-part1 -m "Completion of Part 1"
   git push origin ca1-part1
   ```
   Marks the completion of Part 1 of the assignment with a tag and pushes it to GitHub.

By following these steps, you can manage and version control a software project using Git, ensuring a structured and traceable development process.

In summary, the commands provided demonstrate the initialization of a Git repository, committing changes, managing branches, tagging versions, and pushing to a remote repository. This workflow is crucial for effective version control and collaboration in software development projects.

