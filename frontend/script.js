const searchButton = document.getElementById("search-button");
const usernameInput = document.getElementById("username-input");
const repositoryBody = document.getElementById("repository-body");
const errorMessage = document.getElementById("error-message");
const loadingMessage = document.getElementById("loading-message");

const userLogin = document.getElementById("user-login");
const userName = document.getElementById("user-name");
const userFollowers = document.getElementById("user-followers");
const userRepos = document.getElementById("user-repos");

async function getUser(username) {
    const userResponse = await fetch(
            "https://api.github.com/users/" + username
        );

        if (!userResponse.ok) {
            throw new Error("User not found");
        }

        const data = await userResponse.json();
        return data;


}
async function getRepositories(username){
    const repoResponse = await fetch(
            "https://api.github.com/users/" + username + "/repos"
        );

        if (!repoResponse.ok) {
            throw new Error("Could not fetch repositories");
        }

        const repositories = await repoResponse.json();
        return repositories;
}


searchButton.addEventListener("click", async function() {
    
    const username = usernameInput.value.trim();
    
    if (username === "") {
        console.log("Username cannot be empty.");
        return;
    }
    errorMessage.textContent = "";
    loadingMessage.textContent = "Loading...";
    repositoryBody.innerHTML = "";
    userLogin.textContent = "Username: ";
    userName.textContent = "Name: ";
    userFollowers.textContent = "Followers: ";
    userRepos.textContent = "Repositories: ";
    try {

        // Get user information

        const data = await getUser(username);
        // Display user information
        document.getElementById("user-login").textContent =
            "Username: " + data.login;

        document.getElementById("user-name").textContent =
            "Name: " + data.name;

        document.getElementById("user-followers").textContent =
            "Followers: " + data.followers;

        document.getElementById("user-repos").textContent =
            "Repositories: " + data.public_repos;


        // Get repositories
        const repositories = await getRepositories(username);
        
        // Remove previous repositories
        repositoryBody.innerHTML = "";

        // Add repositories to table
        repositories.forEach(repo => {

            const row = document.createElement("tr");

            const nameCell = document.createElement("td");
            const languageCell = document.createElement("td");
            const starsCell = document.createElement("td");
            const forksCell = document.createElement("td");

            nameCell.textContent = repo.name;
            languageCell.textContent = repo.language;
            starsCell.textContent = repo.stargazers_count;
            forksCell.textContent = repo.forks_count;

            row.appendChild(nameCell);
            row.appendChild(languageCell);
            row.appendChild(starsCell);
            row.appendChild(forksCell);

            repositoryBody.appendChild(row);
        });
        loadingMessage.textContent = "";

    } catch (error) {
        loadingMessage.textContent = "";

    errorMessage.textContent = error.message;


        console.log(error.message);

    }

});