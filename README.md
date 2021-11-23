Demo for implement restful API and paging

UserListFragment for show user list
 using GetUsersModel for calling API and implement DataSourceFactory in it, with UserAdapter recycleView will auto call next page

UserDetailFragment
 call from UserListFragment, when user click list item in it, will call get API to fetch user detail infomation and sent to UserDetailFragment to display into.
