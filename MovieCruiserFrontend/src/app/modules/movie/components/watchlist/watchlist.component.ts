import { Component, OnInit } from '@angular/core';
import { Movie } from '../../movie';
import { MovieService } from '../../movie.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'movie-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.css']
})
export class WatchlistComponent implements OnInit {
  movies: Array<Movie>;
  useWatchlistApi = true;

  constructor(private movieService: MovieService, private matSnackBar: MatSnackBar) {
    this.movies = [];
  }

  ngOnInit() {
    let message = 'Watch List is Empty';
    
    this.movieService.getMyWatchList().subscribe(movies => {
      this.movies.push(...movies);
    },
    error =>{
      this.matSnackBar.open(message, '', { duration: 1000 });
    });
  }

}
