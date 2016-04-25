//
//  SearchViewController.m
//  SjsuBookFinder
//
//  Created by Ravin Sardal on 4/23/16.
//  Copyright © 2016 Ravin Sardal. All rights reserved.
//

#import "SearchViewController.h"

@interface SearchViewController () <UISearchBarDelegate,UITableViewDelegate,UITableViewDataSource>
@property (strong, nonatomic) IBOutlet UISearchBar *searchBar;
@property (strong, nonatomic) IBOutlet UITableView *tableView;
@property (strong, nonatomic) IBOutlet UISegmentedControl *segmentedControl;

@end

@implementation SearchViewController{
    NSArray<NSString *>* segmentedControlItems;
    NSMutableArray<NSString *> *searchResults;
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
    
    //table view setup
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    searchResults = [[NSMutableArray alloc]init];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)searchBarSearchButtonClicked:(UISearchBar *)searchBar{
    NSString *searchString = searchBar.text;
    [self makeHTTPRequestForType:segmentedControlItems[[_segmentedControl selectedSegmentIndex]]
                    forParameter:searchString];
}

- (void)makeHTTPRequestForType:(NSString *)type forParameter:(NSString *)param{
    NSURLComponents *components = [NSURLComponents componentsWithString:@"http://localhost:9999/a"];
    NSURLQueryItem *reqType = [NSURLQueryItem queryItemWithName:@"requesttype" value:@"findbook"];
    
    NSURLQueryItem *isbn = [NSURLQueryItem queryItemWithName:@"isbn" value:@""];
    NSURLQueryItem *title = [NSURLQueryItem queryItemWithName:@"title" value:@""];
    NSURLQueryItem *author = [NSURLQueryItem queryItemWithName:@"author" value:@""];
    
    if ([type isEqualToString:@"Title"]) {
        title = [NSURLQueryItem queryItemWithName:@"title" value:param];
    }else if ([type isEqualToString:@"ISBN"]){
        isbn = [NSURLQueryItem queryItemWithName:@"isbn" value:param];
    }
    
    components.queryItems = @[reqType,isbn,title,author];
    NSURL *url = components.URL;
    
//    __block NSString *dataStr;
//    NSURLSession *session = [NSURLSession sharedSession];
//    [[session dataTaskWithURL:[NSURL URLWithString:url.absoluteString]
//            completionHandler:^(NSData *data,
//                                NSURLResponse *response,
//                                NSError *error) {
//                dataStr = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
//            }] resume];
//    NSLog(@"%@",dataStr);
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    NSURLResponse *response;
    NSError *error;
    
    NSData *responseData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
    
    NSString *responseStr = [[NSString alloc] initWithData:responseData encoding:NSUTF8StringEncoding];
    [self parseServerResponse:responseStr];
}

- (void)parseServerResponse:(NSString *)responseStr{
    NSArray<NSString *> *responseArray = [responseStr componentsSeparatedByString:@"\n"];
    for (NSUInteger i=0; i<responseArray.count; i++) {
        [searchResults addObject:responseArray[i]];
    }
}

#pragma mark - UITableViewDataSource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return [searchResults count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell *cell = [_tableView dequeueReusableCellWithIdentifier:@"result"];
    if (cell == nil) {
        cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"result"];
    }
    cell.textLabel.text = [searchResults objectAtIndex:indexPath.row];
    
    return cell;
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