import { Component, OnInit, Input } from '@angular/core';
import { Movie } from '../../movie';
import { MovieService } from '../../movie.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'movie-container',
  templateUrl: './container.component.html',
  styleUrls: ['./container.component.css']
})
export class ContainerComponent implements OnInit {
  @Input()
  movies: Array<Movie>;

  @Input()
  useWatchlistApi: boolean;

  constructor(private movieService: MovieService, private matSnackBar: MatSnackBar) {
  }

  ngOnInit() {
  }

  addMovieToWatchlist(movie) {
    console.log("Movie Title", movie.title);
    let message = `${movie.title} added to watchlist`;

    this.movieService.addMovieToWatchList(movie).subscribe(movie =>{
      this.matSnackBar.open(message, '', { duration: 1000 });
    });
  }

  deleteFromWatchlist(movie) {
    let message = `${movie.title} deleted from your watchlist`;

    // for(var i=0; i<this.movies.length; i++) {
    //   if(this.movies[i].title === movie.title) {
    //     this.movies.splice(i,1);
    //   }
    // }

    this.movieService.deleteFromMyWatchlist(movie).subscribe(result =>{
      this.matSnackBar.open(message, '', { duration: 1000 });

      const index = this.movies.indexOf(movie);
      this.movies.splice(index, 1);
    });
  }

}
