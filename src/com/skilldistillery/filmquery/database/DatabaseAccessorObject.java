package com.skilldistillery.filmquery.database;

import java.sql.*;
import java.util.*;
import com.skilldistillery.filmquery.entities.*;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	String user = "student";
	String pass = "student";

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT film.*, language.name FROM film JOIN language ON film.language_id = language.id WHERE film.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);

			ResultSet filmResult = stmt.executeQuery();

			if (filmResult.next()) {
				film = new Film();
				film.setId(filmResult.getInt("id"));
				film.setTitle(filmResult.getString("title"));
				film.setDescription(filmResult.getString("description"));
				film.setReleaseYear(filmResult.getString("release_year"));
				film.setLanguageID(filmResult.getInt("language_id"));
				film.setLanguageType(filmResult.getString("language.name"));
				film.setRating(filmResult.getString("rating"));
				film.setActors(findActorsByFilmId(filmResult.getInt("id")));
			}
			filmResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("Database Error");
			e.printStackTrace();
		}
		return film;
	}

	@Override
	public Actors findActorById(int actorId) {
		Actors actors = null;
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT * FROM film WHERE id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);

			ResultSet filmResult = stmt.executeQuery();

			if (filmResult.next()) {
				actors = new Actors();
				actors.setId(filmResult.getInt("id"));
				actors.setFirstName(filmResult.getString("first_name"));
				actors.setLastName(filmResult.getString("last_name"));
			}
			filmResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("Database Error");
			e.printStackTrace();
		}
		return actors;
	}

	@Override
	public List<Actors> findActorsByFilmId(int filmId) {
		List<Actors> actorByFilmId = new ArrayList<>();
		Actors actorID = null;
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT a.id, a.first_name, a.last_name, film.id" + " FROM film_actor f" + " JOIN actor a ON f.actor_id = a.id" + " JOIN film ON f.film_id = film.id" + " WHERE film.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);

			ResultSet filmResult = stmt.executeQuery();

			while (filmResult.next()) {
				actorID = new Actors();
				actorID.setId(filmResult.getInt("id"));
				actorID.setFirstName(filmResult.getString("first_name"));
				actorID.setLastName(filmResult.getString("last_name"));
				actorByFilmId.add(actorID);
			}
			filmResult.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			System.out.println("Database Error");
			e.printStackTrace();
		}
		return actorByFilmId;
	}

	public List<Film> findFilmByKeyword(String keyword) {
		Film film = null;
		List<Film> films = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);

			String sql = "SELECT * FROM film f JOIN film_category fc ON f.id = fc.film_id JOIN category c ON fc.category_id = c.id JOIN language l ON f.language_id = l.id WHERE title LIKE ? OR description LIKE ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			keyword = "%" + keyword + "%";
			stmt.setString(1, keyword);
			stmt.setString(2, keyword);

			ResultSet filmResult = stmt.executeQuery();

			while (filmResult.next()) {
				film = new Film();
				film.setId(filmResult.getInt("id"));
				film.setTitle(filmResult.getString("title"));
				film.setDescription(filmResult.getString("description"));
				film.setReleaseYear(filmResult.getString("release_year"));
				film.setLanguageID(filmResult.getInt("language_id"));
				film.setLength(filmResult.getInt("length"));
				film.setRating(filmResult.getString("rating"));
				film.setActors(findActorsByFilmId(filmResult.getInt("id")));
				film.setLanguageType(filmResult.getString("l.name"));
				films.add(film);
			}
			filmResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("Database Error");
			e.printStackTrace();
		}
		return films;
	}

}
