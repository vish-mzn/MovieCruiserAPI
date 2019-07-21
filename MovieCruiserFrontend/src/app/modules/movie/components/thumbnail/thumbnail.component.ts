import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { Movie } from '../../movie';
import { MovieDialogComponent } from '../movie-dialog/movie-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'movie-thumbnail',
  templateUrl: './thumbnail.component.html',
  styleUrls: ['./thumbnail.component.css']
})
export class ThumbnailComponent implements OnInit {
  @Input()
  movie: Movie;
  @Input()
  useWatchlistApi: boolean;
  @Output()
  addMovie = new EventEmitter();
  @Output()
  deleteMovie = new EventEmitter();

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
  }

  addToWatchlist() {
    this.addMovie.emit(this.movie);
  }

  deleteFromWatchlist() {
    this.deleteMovie.emit(this.movie);
  }

  updateFromWatchlist(actionType) {
    console.log('Movie is getting updated');
    let dialogRef = this.dialog.open(MovieDialogComponent, {
      width: '400px',
      data: { obj: this.movie, actionType: actionType }
    });

    console.log('Open dialog');
    dialogRef.afterClosed().subscribe(result =>{
      console.log('The dialog was closed');
    });
  }
  
}
