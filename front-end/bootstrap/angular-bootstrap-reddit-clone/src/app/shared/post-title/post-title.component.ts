import { Component, OnInit } from '@angular/core';
import { PostModel } from '../../shared/post-model';
import { PostService } from '../../shared/post.service';
import { faComments } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-post-title',
  templateUrl: './post-title.component.html',
  styleUrls: ['./post-title.component.css']
})
export class PostTitleComponent implements OnInit {

  faComments = faComments;

  listOfPosts: Array<PostModel> = [];

  constructor(private postService: PostService) {

    this.postService.getAllPosts().subscribe(postsList => {
      this.listOfPosts = postsList;
    });

  }

  ngOnInit(): void {
  }

}
