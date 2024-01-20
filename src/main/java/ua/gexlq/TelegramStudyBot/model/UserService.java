package ua.gexlq.TelegramStudyBot.model;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import ua.gexlq.TelegramStudyBot.handler.Logger;

@Component
public class UserService {

	private String defaultLanguage = "uk";

	public enum UserState {
		MAIN_MENU, WORK_MENU, HELP_MENU, SETTINGS_MENU, MATERIALS_MENU;
	}

	private UserState defaultState = UserState.MAIN_MENU;

	@Autowired
	Repository repository;

	public boolean isUserEmpty(long userId) {
		User user = repository.findById(userId).get();
		
		return user.getFaculty() != null && user.getSpecialization() != null && user.getSemester() != null;
	}

	public boolean isUserRegister(long userId) {
		return repository.existsById(userId);
	}

	public boolean isUserFacultySet(long userId) {
		User user = repository.findById(userId).get();

		return user.getFaculty() != null;
	}

	public boolean isUserSemesterSet(long userId) {
		User user = repository.findById(userId).get();

		return user.getSemester() != null;
	}

	public boolean isUserSpecializationSet(long userId) {
		User user = repository.findById(userId).get();

		return user.getSpecialization() != null;
	}

	public void saveUser(User user) {
		repository.save(user);
	}

	public void registerUser(Message message) {

			var chatId = message.getChatId();

			var chat = message.getChat();

			User user = new User();

			user.setChatId(chatId);

			user.setNickName(chat.getUserName());
			user.setFirstName(chat.getFirstName());
			user.setLastName(chat.getLastName());

			user.setLanguage(defaultLanguage);

			user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));

			user.setUserState(defaultState);

			Logger.logUser(user);

			saveUser(user);
	}

	public void setUserSemester(long userId, String semester) {
			User user = repository.findById(userId).get();
			user.setSemester(semester);

			saveUser(user);
	}

	public void setUserFaculty(long userId, String faculty) {
			User user = repository.findById(userId).get();
			user.setFaculty(faculty);

			saveUser(user);
	}

	public void setUserSpecialization(long userId, String specialization) {
			User user = repository.findById(userId).get();
			user.setSpecialization(specialization);

			saveUser(user);
	}

	public void setUserLanguage(long userId, String locale) {
			User user = repository.findById(userId).get();
			user.setLanguage(locale);

			saveUser(user);
	}

	public void setUserState(long userId, UserState state) {
			User user = repository.findById(userId).get();
			user.setUserState(state);

			saveUser(user);
	}

	public User getUser(long userId) {
		return repository.findById(userId).get();
	}

	public String getUserNickName(long userId) {
		Optional<User> user = repository.findById(userId);
		return user.get().getNickName();
	}

	public String getUserFirstName(long userId) {
		Optional<User> user = repository.findById(userId);
		return user.get().getFirstName();
	}

	public String getUserLastName(long userId) {
		Optional<User> user = repository.findById(userId);
		return user.get().getLastName();
	}

	public String getUserFaculty(long userId) {
		Optional<User> user = repository.findById(userId);
		return user.get().getFaculty();
	}

	public String getUserFacultyCode(long userId) {
		Optional<User> user = repository.findById(userId);
		return "faculty." + user.get().getFaculty();
	}

	public String getUserSemester(long userId) {
		Optional<User> user = repository.findById(userId);
		return user.get().getSemester();
	}

	public String getUserSpecialization(long userId) {
		Optional<User> user = repository.findById(userId);
		return user.get().getSpecialization();
	}

	public String getUserLanguage(long userId) {
		Optional<User> user = repository.findById(userId);
		return user.get().getLanguage();
	}

	public UserState getUserState(long userId) {
		Optional<User> user = repository.findById(userId);
		return user.get().getUserState();
	}

	public Timestamp getUserRegisterAt(long userId) {
		Optional<User> user = repository.findById(userId);
		return user.get().getRegisteredAt();
	}

}
