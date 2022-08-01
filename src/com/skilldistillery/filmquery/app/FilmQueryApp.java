package com.skilldistillery.filmquery.app;

import java.sql.*;
import java.util.*;
import com.skilldistillery.filmquery.database.*;
import com.skilldistillery.filmquery.entities.*;

public class FilmQueryApp {
	Scanner input = new Scanner(System.in);

	DatabaseAccessor dba = new DatabaseAccessorObject();

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
//      app.test();
		app.launch();
	}

	private void test() {
		Film film = dba.findFilmById(1);
		System.out.println(film);
	}

	private void launch() {
		startUserInterface(input);
		input.close();
	}

	private void startUserInterface(Scanner input) {
		FilmQueryApp app = new FilmQueryApp();

		boolean loop = true;

		while (loop) {
			System.out.println("MENU");
			System.out.println("1. Look up film by ID");
			System.out.println("2. Look up film by Keyword");
			System.out.println("3. Exit");

			int userInput = 0;
			userInput = input.nextInt();

			if (userInput < 4) {
				switch (userInput) {

				case 1:
					System.out.println("Film by ID: ");
					int id = input.nextInt();
					Film film = dba.findFilmById(id);

					if (film != null) {
						System.out.println("Film: " + film.getTitle() + "\nRelease Year: " + film.getReleaseYear()
								+ "\nRating: " + film.getRating() + "\nDescription: " + film.getDescription()
								+ "\nLanguage: " + film.getLanguageType());
					} else {
						System.out.println("Error: Enter valid Film Id.");
					}
					break;

				case 2:
					System.out.println("Search keyword: ");
					String keyword = input.next();
					List<Film> film3 = dba.findFilmByKeyword(keyword);

					for (Film film2 : film3) {
						System.out.println(film2.films());
					}
					break;

				case 3:
					System.out.println("Goodbye");
					System.exit(0);
					break;
				}
			}
		}
	}

}
