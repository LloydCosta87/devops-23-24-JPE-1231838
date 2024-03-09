
# Class Assignment 1

## Introduction

This report outlines the process of managing a software project using Git, detailing the creation of a GitHub repository, initial setup, and the development of new features following a structured version control system.

### Working with GitHub Issues

GitHub Issues is a feature that allows you to track and manage tasks, enhancements, and bugs in your projects. It's considered a best practice to utilize GitHub Issues for organizing work and keeping track of project progress.

### Creating and Linking Issues to Commits

1. **Creating Issues:** It's a good practice to create an issue for any new feature, bug fix, or enhancement. This helps in documenting the need or problem and facilitates discussion.

2. **Marking Commits with Issues:** When making a commit that addresses an issue, it's helpful to reference the issue number in the commit message. This can be done by including the issue number prefixed with a hash symbol (#) and a brief description of the commit. For example:
   
   ```bash
   git commit -m "#1 - First commit addressing the issue"


## Initial Setup

To start, create a new repository on GitHub and initialize it locally:

1. **Create README.md and Initialize Repository:**
   ```bash
   echo "# devops-23-24-JPE-1231838" >> README.md
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
   git commit -m "#1 first commit"
   ```
   Stages the `README.md` file and commits it with a message.

4. **Set Main as Default Branch:**
   ```bash
   git branch -M main
   ```
   Renames the default branch to `main`.

5. **Add Remote Repository:**
   ```bash
   git remote add origin git@github.com:LloydCosta87/devops-23-24-JPE-1231838.git
   ```
   Links your local repository with the remote GitHub repository.

6. **Push Changes to GitHub:**
   ```bash
   git push -u origin main
   ```
   Pushes the commit to GitHub, setting `main` as the upstream branch.

   ### Importance of `.gitignore` Before Starting Work

Setting up a `.gitignore` file before starting any development work is crucial for several reasons:

1. **Repository Cleanliness:** It keeps your repository clean by excluding unnecessary files like build artifacts, log files, or temporary files, ensuring your repository focuses only on source code and essential resources.

2. **Security:** Helps prevent the accidental commit of sensitive information, such as configuration files containing passwords or API keys, enhancing the security of your project.

3. **Performance Efficiency:** Ignoring large or unnecessary files improves the performance of Git operations, making the development process more efficient.

4. **Collaboration Consistency:** Ensures all team members ignore the same files, reducing merge conflicts and focusing pull requests on meaningful changes.

5. **Workflow Customization:** Allows tailoring to the specific needs of your project and development environment by excluding files relevant to your workflow.

### Setting Up `.gitignore`

1. **Create the File:** At the root of your Git repository, create a `.gitignore` file.
2. **Define Patterns:** Add patterns to specify which files and directories should be ignored (e.g., `*.log`, `build/`, `.env`).
3. **Commit `.gitignore`:** Commit the file to your repository to ensure your ignore rules are active from the start.

```bash
git add .gitignore
git commit -m "Add .gitignore file"
```


## Task 1: Managing the Main Branch

Using only the `main` branch, the following steps were undertaken:

1. **Copy Tutorial Code:**
   ```bash
   cp -r path/to/TutorialReactSpringDataREST ./CA1
   ```
   Copies the tutorial project into a new folder named `CA1`.

2. **Commit and Push Initial Code:**
   ```bash
   git add .
   git commit -m "(#number of the issue) Initial project setup"
   git push origin main
   ```
   Adds all files to staging, commits them to the repository, and pushes to the `main` branch on GitHub.

3. **Tagging Initial Version:**
   ```bash
   git tag v1.1.0 
   git push origin v1.1.0
   ```
   Tags the initial version of the application and pushes the tag to GitHub.

4. **Develop New Feature and Commit:**
   After developing a new feature (e.g., adding a `jobYears` field), and testing it thoroughly:
   ```bash
   git add .
   git commit -m "(#number of the issue) Added jobYears field"
   git push origin main
   ```
   Stages the changes, commits them with a descriptive message, and pushes to the main branch.

   Before committing, you can use:
   ```bash
   git status
   ```
   to review which changes are staged for the next commit. This command provides visibility into your working directory and staging area, showing which changes will be included in the next commit.

6. **Tagging Feature Release:**
   ```bash
   git tag -a v1.2.0 -m "(#number of the issue) Added new feature: jobYears"
   git push origin v1.2.0
   ```
   Tags the new feature release and pushes the tag to GitHub.

7. **Marking Assignment Completion:**
   ```bash
   git tag  ca1-part1 
   git push origin ca1-part1
   ```
   Marks the completion of Part 1 of the assignment with a tag and pushes it to GitHub.

By following these steps, you can manage and version control a software project using Git, ensuring a structured and traceable development process.


# Simple Git Workflow Scenario

This scenario illustrates a simple Git workflow for managing stable versions and feature development.

1. **Use the master branch for stable versions:**
   - Ensure you're on the master branch and it's up to date.
     ```bash
     git checkout main
     git pull origin main
     ```

2. **Develop new features in separate branches:**
   - Create a branch named `email-field`:
     ```bash
     git checkout -b email-field
     ```
   - After adding support for the email field and implementing unit tests:
     ```bash
     git add .
     git commit -m "(#number of the issue) Add email field feature and unit tests"
     ```
   - Merge the new feature into the master branch and tag it:
     ```bash
     git checkout main
     git merge email-field
     git tag v1.3.0
     git push origin main
     git push origin v1.3.0
     ```
     To see how many branches exist in your local Git repository, you can use the following command:
       ```bash
       git branch
        ```
       This command lists all the local branches in your repository. If you want to see both local and remote branches, you can use:
         ```bash
        git branch -a
        ```

3. **Create branches for fixing bugs:**
   - Create a branch named `fix-invalid-email`:
     ```bash
     git checkout -b fix-invalid-email
     ```
   - After debugging and ensuring validation for the email field:
     ```bash
     git add .
     git commit -m "Fix invalid email validation"
     ```
   - Merge the fix into the master branch and update the tag:
     ```bash
     git checkout main
     git merge fix-invalid-email
     git tag -a v1.3.1 -m "Fix email validation issue"
     git push origin main
     git push origin v1.3.1
     ```

4. **Mark the end of the assignment with a tag:**
   - Tag the repository to mark the completion of the assignment:
     ```bash
     git tag -a ca1-part2 -m "Completion of Part 2"
     git push origin ca1-part2
     ```




