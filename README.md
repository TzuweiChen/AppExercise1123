# Demo for implement restful API and paging

## UserListFragment

using GetUsersModel for calling API and implement DataSourceFactory in it, with UserAdapter recycleView will auto call next page

![](https://github.com/TzuweiChen/AppExercise1123/blob/master/list.png)


## UserDetailFragment

call from UserListFragment, when user click list item in it, will call get API to fetch user detail infomation and sent to UserDetailFragment to display into.

![](https://github.com/TzuweiChen/AppExercise1123/blob/master/detail.png)


#### Todo

- PagedListAdapter、PagedList、LivePagedListBuilder is Deprecated, using newets to replace it
- Using kotlin
