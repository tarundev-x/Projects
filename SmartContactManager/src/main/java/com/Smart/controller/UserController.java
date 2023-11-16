package com.Smart.controller;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Smart.dao.ContactRepository;
import com.Smart.dao.UserRepository;
import com.Smart.entities.Contact;
import com.Smart.entities.User;
import com.Smart.helper.Message;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ContactRepository contactRepository;

	// method for adding common data to response
	@ModelAttribute
	public void AddCommondata(Model model, Principal principal) {

		String username = principal.getName();
		System.out.println("username " + username);

		// get the user using email
		User user = userRepository.getUserByByUserName(username);

		System.out.println("USER " + user);
		model.addAttribute("user", user);

	}

	// dashboard home

	@RequestMapping("/index")
	public String dashboard(Model model, Principal primcipal) {

		model.addAttribute("title", "User Dashboard");
		return "normal/user_dashboard";
	}

	// open add form handler
	@GetMapping("/add-contact")
	public String OpenAddContactform(Model model) {

		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());
		return "normal/add_contact_form";
	}

	// processing add contact
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact, @RequestParam("ProfileImage") MultipartFile file,
			Principal principal, HttpSession session) {
		try {

			String name = principal.getName();
			User user = this.userRepository.getUserByByUserName(name);

			// processing and uploading file

			if (file.isEmpty()) {
				// if the file is empty then try our message
				System.out.println("file is empty");
				contact.setImage("contact.png");
			} else {
				// file the file to folder and update the name to contact
				contact.setImage(file.getOriginalFilename());
				File savefile = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(savefile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Image is uploaded");
			}
			contact.setUser(user);

			user.getContacts().add(contact);
			this.userRepository.save(user);

			System.out.println("DATA " + contact);
			System.out.println("Added to database");

			// Set the success message
			com.Smart.helper.Message successMessage = new com.Smart.helper.Message(
					"Your contact is added !! Add more..", "success");
			session.setAttribute("message", successMessage);

		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();

			// essage error...
			com.Smart.helper.Message errorMessage = new com.Smart.helper.Message("Something went wrong !! Try again..",
					"danger");
			session.setAttribute("message", errorMessage);

		}
		return "normal/add_contact_form";
	}

	// show contacts handler

	@GetMapping("/show-contacts/{page}")
	public String ShowContacts(@PathVariable("page") Integer page, Model m, Principal principal) {
		// send contact list
		m.addAttribute("title", "Show User Contacts");
		String username = principal.getName();
		User user = this.userRepository.getUserByByUserName(username);
//		perpage = 5[n]
		// current page = 0 [page]

		Pageable pageable = PageRequest.of(page, 6);
		Page<Contact> contacts = this.contactRepository.findContactsByUser(user.getId(), pageable);
		m.addAttribute("contacts", contacts);
		m.addAttribute("currentpage", page);
		m.addAttribute("TotalPages", contacts.getTotalPages());

		return "normal/show_contacts";
	}

	// showing particular contact details
	@RequestMapping("/{cid}/contacts")
	public String showContactDetails(@PathVariable("cid") Integer cid, Model model, Principal principal) {
		System.out.println("cid " + cid);
		Optional<Contact> contactOp = this.contactRepository.findById(cid);

		if (contactOp.isPresent()) { // Check if contactOp contains a value
			Contact contact = contactOp.get();

			String username = principal.getName();
			User user = this.userRepository.getUserByByUserName(username);

			if (user.getId() == contact.getUser().getId()) {
				model.addAttribute("contact", contact);
				model.addAttribute("title", contact.getName());
			}
		}

		return "normal/contact_detail";
	}

	// deleting contact handler
	@GetMapping("/delete/{cid}")
	public String deleteContact(@PathVariable("cid") Integer cid, Principal principal, Model model,
			HttpSession session) {
		try {
			Optional<Contact> contactOptional = this.contactRepository.findById(cid);

			if (contactOptional.isPresent()) {
				Contact contact = contactOptional.get();
				String username = principal.getName();
				User user = this.userRepository.getUserByByUserName(username);

				// Check if the logged-in user is the owner of the contact
				if (user.getId() == contact.getUser().getId()) {
					// Delete the contact from the database

//					contact.setUser(null);
//					this.contactRepository.delete(contact);

					User user2 = this.userRepository.getUserByByUserName(principal.getName());
					user2.getContacts().remove(contact);
					this.userRepository.save(user2);

					// Store the image path before deleting the contact
					String image = contact.getImage();

					// Delete the image file
					if (!image.equals("contact.png")) {
						File imageFile = new File("your_image_directory_path/" + image);
						if (imageFile.exists()) {
							imageFile.delete();
						}
					}

					session.setAttribute("message", new Message("Contact deleted successfully.", "success"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); // Add this line to print the exception to the console
			session.setAttribute("message", new Message("An error occurred while deleting the contact.", "danger"));
		}

		System.out.println("Redirecting to /show-contacts/0"); // Add this line to check the redirection

		return "redirect:/user/show-contacts/0";
	}

	// open update form handler
	@PostMapping("/update-contact/{cid}")
	public String updateForm(@PathVariable("cid") Integer cid, Model model) {
		model.addAttribute("title", "Update Contact");
		Contact contact = this.contactRepository.findById(cid).get();

		model.addAttribute("contact", contact);
		return "normal/update_form";
	}

	// update contact handler

	@PostMapping("/process-update")
	public String updateHandler(@ModelAttribute Contact contact, @RequestParam("ProfileImage") MultipartFile file,
			Model model, HttpSession session, Principal principal) {
		try {
			// Get the old contact details
			Contact oldContactDetail = this.contactRepository.findById(contact.getCid()).get();

			// Check if any changes were made to contact information (excluding image)
			if (!contact.equals(oldContactDetail)) {
				// Changes were made, set the message
				session.setAttribute("message", new Message("Contact details updated", "success"));
			}

			if (!file.isEmpty()) {
				File deletefile = new ClassPathResource("static/img").getFile();
				File file1 = new File(deletefile, oldContactDetail.getImage());
				file1.delete();

				// Update the new profile image
				File saveFile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				contact.setImage(file.getOriginalFilename());
			} else {
				contact.setImage(oldContactDetail.getImage());
			}

			User user = this.userRepository.getUserByByUserName(principal.getName());
			contact.setUser(user);
			this.contactRepository.save(contact);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/user/" + contact.getCid() + "/contacts";
	}

	// your profile handler
	@GetMapping("/profile")
	public String yourProfile(Model model) {
		model.addAttribute("title", "Profile Page");
		return "normal/profile"; // Remove the leading slash
	}

	// setting handler
	@GetMapping("/settings")
	public String OpenSetting() {
		return "normal/settings";
	}

	// change password handler
	@PostMapping("/change-password")
	public String ChangePassword(@RequestParam("oldpassword") String oldpassword,
			@RequestParam("newpassword") String newpassword, Principal principal,HttpSession session) {
		System.out.println("oldpassword - " + oldpassword);
		System.out.println("newpassword - " + newpassword);
		String Username = principal.getName();
		User currentUser = this.userRepository.getUserByByUserName(Username);
		if(this.bCryptPasswordEncoder.matches(oldpassword, currentUser.getPassword()))
		{
			//change password
			currentUser.setPassword(this.bCryptPasswordEncoder.encode(newpassword));
			this.userRepository.save(currentUser);
			session.setAttribute("message", new Message("Your password changed successfully...", "success"));
		}
		else
		{
			session.setAttribute("message", new Message("Please Enter correct old password !!", "error"));
			return "redirect:/user/settings";
		}
		

		return "redirect:/user/index";
	}

}
