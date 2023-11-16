
//sidebar
const toggleSidebar = () => {
	const sidebar = document.querySelector('.sidebar');
	const content = document.querySelector('.content');

	if (sidebar.style.display === "block") {
		sidebar.style.display = "none";
		content.style.marginLeft = "0%";
	} else {
		sidebar.style.display = "block";
		content.style.marginLeft = "20%";
	}
};



// Display the alert when the page loads (you can trigger it as needed)
/*
window.onload = function() {
	var alertElement = document.querySelector('.alert');
	alertElement.style.display = 'block';
	// Automatically hide the alert after a few seconds (adjust the timeout as needed)
	setTimeout(function() {
		alertElement.style.display = 'none';
	}, 3000); // Hide after 5 seconds
};
*/

document.addEventListener('DOMContentLoaded', function() {
	var alertElement = document.querySelector('.alert');
	if (alertElement) {
		alertElement.style.display = 'block';
		setTimeout(function() {
			alertElement.style.display = 'none';
		}, 3000); // Hide after 3 seconds
	}
});


//search functionality
const search = () => {
	let query = $("#search-input").val();

	if (query.trim() === "") {
		// If the query is empty or only contains spaces, hide the search results container
		$(".search-result").hide();
	} else {
		// If the query is not empty, log it to the console and show the search results container
		console.log('Query:', query);
		let url = `http://localhost:3030/search/${query}`;

		fetch(url)
			.then((response) => {
				if (!response.ok) {
					throw new Error(`HTTP error! Status: ${response.status}`);
				}
				return response.json();
			})
			.then((data) => {
				// Update the content of .search-result with the fetched data
				console.log('Data:', data);

				if (Array.isArray(data)) {
					let text = '<div class="list-group">';

					data.forEach((contact) => {
						text += `<a href="/user/${contact.cid}/contacts" class="list-group-item list-group-item-action">${contact.name}</a>`;


					});
					text += '</div>';
					$(".search-result").html(text);
					$(".search-result").show();
				} else {
					// Handle the case where data is not an array (e.g., display an error message)
					console.error('Invalid data format:', data);
					$(".search-result").hide(); // Hide the results container in case of error
				}
			})
			.catch((error) => {
				console.error('Fetch error:', error);
			});
	}
};













