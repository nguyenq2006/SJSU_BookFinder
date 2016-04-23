//
//  SearchViewController.m
//  SjsuBookFinder
//
//  Created by Ravin Sardal on 4/23/16.
//  Copyright © 2016 Ravin Sardal. All rights reserved.
//

#import "SearchViewController.h"

@interface SearchViewController () <UISearchBarDelegate>
@property (strong, nonatomic) IBOutlet UISearchBar *searchBar;
@property (strong, nonatomic) IBOutlet UITableView *tableView;
@property (strong, nonatomic) IBOutlet UISegmentedControl *segmentedControl;

@end

@implementation SearchViewController{
    NSArray<NSString *>* segmentedControlItems;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.navigationItem.title = @"Search";
    self.searchBar.delegate = self;
    segmentedControlItems = @[@"Title",@"ISBN"];
    [_segmentedControl removeAllSegments];
    for (NSUInteger i=0; i<segmentedControlItems.count; i++) {
        [_segmentedControl insertSegmentWithTitle:segmentedControlItems[i] atIndex:i animated:NO];
    }
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)searchBarSearchButtonClicked:(UISearchBar *)searchBar{
    NSString *searchString = searchBar.text;
    [_segmentedControl selectedSegmentIndex];
}


/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
