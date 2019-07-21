import { Component, OnInit, Inject } from '@angular/core';
import { MovieService } from '../../movie.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Movie } from '../../movie';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'movie-dialog',
  templateUrl: './movie-dialog.component.html',
  styleUrls: ['./movie-dialog.component.css']
})
export class MovieDialogComponent implements OnInit {
  movie: Movie;
  comments: string;
  actionType:string;

  constructor(private movieService: MovieService, private matSnackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<MovieDialogComponent> ) 
  {
    this.comments = data.obj.movieComments;
    this.movie = data.obj;
    this.actionType = data.actionType;
  }

  ngOnInit() {
    console.log(this.data);
  }

  onNoClick() {
    this.dialogRef.close();
  }

  updateComments() {
    console.log("comments", this.comments);
    this.movie.comments = this.comments;
    this.dialogRef.close();
    this.movieService.updateComments(this.movie).subscribe(movie =>{
      this.matSnackBar.open('Movie updated to watchlist successfully', '', { duration: 1500 });
    });
  }

}
