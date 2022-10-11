package io.github.vgarden;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class Bot {

	private final static Path CONFIG_DIR = Path.of("/config");

	public static void main (String[] args) {
		String token = loadToken();

		DiscordApi api = new DiscordApiBuilder()
			.setToken(token)
			.login()
			.join();

		// register callbacks
		api.addMessageCreateListener(ev -> ev.getChannel().sendMessage(":)"));
	}

	private static String loadToken () {
		InputStream inputStream = Bot.class.getResourceAsStream(CONFIG_DIR.resolve("token").toString());

		if (inputStream == null) {
			throw new RuntimeException("No token file found in /config/token");
		}

		try (
			InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
			BufferedReader reader = new BufferedReader(streamReader)
		) {
			return reader.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
