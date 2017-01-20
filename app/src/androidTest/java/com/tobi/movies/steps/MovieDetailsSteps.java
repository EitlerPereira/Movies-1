package com.tobi.movies.steps;

import android.support.test.InstrumentationRegistry;

import com.tobi.movies.Dependencies;
import com.tobi.movies.EspressoDependencies;
import com.tobi.movies.MovieApplication;
import com.tobi.movies.backend.ConfigurableBackend;
import com.tobi.movies.posterdetails.ApiMovieDetails;

import java.util.Map;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;

public class MovieDetailsSteps {

    @Given("^the following remote movie details exist$")
    public void the_following_remote_movie_details_exist(final DataTable dataTable) {
        extractMovieDetailsFromDataTable(dataTable);
    }

    private void extractMovieDetailsFromDataTable(DataTable dataTable) {
        ConfigurableBackend configurableBackend = getConfigurableBackend();

        for (final Map<String, String> row : dataTable.asMaps(String.class, String.class)) {

            Long movieId = Long.valueOf(row.get("movieId"));
            String posterPath = row.get("posterPath");
            String title = row.get("title");
            String description = row.get("description");
            String releaseDate = row.get("releaseDate");

            configurableBackend.addMovieDetails(createMovieDetails(movieId, title, description, posterPath, releaseDate));
        }
    }

    private ApiMovieDetails createMovieDetails(long movieId, String movieTitle, String movieOverview, String posterPath, String releaseDate) {
        ApiMovieDetails apiMovieDetails = new ApiMovieDetails();
        apiMovieDetails.originalTitle = movieTitle;
        apiMovieDetails.movieId = movieId;
        apiMovieDetails.overview = movieOverview;
        apiMovieDetails.posterPath = posterPath;
        apiMovieDetails.releaseDate = releaseDate;
        return apiMovieDetails;
    }

    private ConfigurableBackend getConfigurableBackend() {
        MovieApplication movieApplication = (MovieApplication) InstrumentationRegistry.getTargetContext().getApplicationContext();
        Dependencies dependencies = movieApplication.getDependencies();
        return ((EspressoDependencies) dependencies).getConfigurableBackend();
    }
}
