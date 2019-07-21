import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, retry } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { Movie } from './movie';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  tmdbEndpoint: string;
  imagePrefix: string;
  apiKey: string;
  watchlistEndpoint: string;
  springEndpoint: string;
  search: string;

  constructor(private http: HttpClient) {
    this.apiKey = 'api_key=a4b2588b06aee0c78edf5ffc29cde805';
    this.tmdbEndpoint = 'https://api.themoviedb.org/3/movie';
    this.imagePrefix = 'https://image.tmdb.org/t/p/w500';
    this.watchlistEndpoint= 'http://localhost:3000/watchlist';
    this.springEndpoint ='http://localhost:8085/api/movieservice/movie';
    this.search = 'https://api.themoviedb.org/3/search/movie?'
  }

  getMovies(type: string, page: number = 1): Observable<Array<Movie>> {
    const endpoint = `${this.tmdbEndpoint}/${type}?${this.apiKey}&page=${page}`;
    console.log(endpoint);
    return this.http.get(endpoint).pipe(
      retry(3),
      map(this.pickMovieResults),
      map(this.transformPosterPath.bind(this))
    );
  }
  transformPosterPath(movies): Array<Movie> {
    return movies.map(movie => {
      movie.poster_path = `${this.imagePrefix}${movie.poster_path}`;
      return movie;
    });
  }
  pickMovieResults(response) {
    return response['results'];
  }

  
  addMovieToWatchList(movie) {
    return this.http.post(this.springEndpoint, movie);
  }

  deleteFromMyWatchlist(movie) {
    const url = `${this.springEndpoint}/${movie.id}`;
    return this.http.delete(url, {responseType: 'text'});
  }

  getMyWatchList(): Observable<Array<Movie>> {
    return this.http.get<Array<Movie>>(this.springEndpoint);
  }

  updateComments(movie) {
    const url = `${this.springEndpoint}/${movie.id}`;
    return this.http.put(url, movie);
  }

  searchMovies(searchKey: string): Observable<Array<Movie>> {
    if(searchKey.length>0) {
      const url = `${this.search}${this.apiKey}&language=en-US&page=1&include_adult=true&query=${searchKey}`;

      return this.http.get(url).pipe(
        retry(3),
        map(this.pickMovieResults),
        map(this.transformPosterPath.bind(this))
      );
    }
  }

}
