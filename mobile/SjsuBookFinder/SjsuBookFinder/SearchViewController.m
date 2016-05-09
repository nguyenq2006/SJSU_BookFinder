//
//  SearchViewController.m
//  SjsuBookFinder
//
//  Created by Ravin Sardal on 4/23/16.
//  Copyright © 2016 Ravin Sardal. All rights reserved.
//

#import "SearchViewController.h"
#import "FindBookParser.h"

/**
 *  Controller to handle the searching of textbooks from the search view
 */
@interface SearchViewController () <UISearchBarDelegate,UITableViewDelegate,UITableViewDataSource>
@property (strong, nonatomic) IBOutlet UISearchBar *searchBar;
@property (strong, nonatomic) IBOutlet UITableView *tableView;
@property (strong, nonatomic) IBOutlet UISegmentedControl *segmentedControl;

@end

@implementation SearchViewController{
    NSArray<NSString *>* segmentedControlItems;
    NSMutableArray<NSString *> *searchResults;
    FindBookParser *fbParser;
}
/**
 *  Do any additional setup after loading the view.
 */
- (void)viewDidLoad {
    [super viewDidLoad];
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

/**
 *  Dispose of any resources that can be recreated.
 */
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

#pragma mark - UISearchBarDelegate

/**
 *  Delegat method for the search bar search button
 *
 *  @param searchBar the search bar pointer
 */
- (void)searchBarSearchButtonClicked:(UISearchBar *)searchBar{
    //clear the search results array before doing another search
    if (searchResults != nil) {
        [searchResults removeAllObjects];
    }
    
    NSString *searchString = searchBar.text;
    if ([_segmentedControl selectedSegmentIndex] == UISegmentedControlNoSegment) {
        UIAlertController *alertController = [UIAlertController alertControllerWithTitle:@"Please select search query type!" message:@"Choose Title or ISBN" preferredStyle:UIAlertControllerStyleAlert];
        UIAlertAction *defaultAction = [UIAlertAction actionWithTitle:@"Ok" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
            
        }];
        [alertController addAction:defaultAction];
        [self presentViewController:alertController animated:YES completion:nil];
    }else{
        [self makeHTTPRequestForType:segmentedControlItems[[_segmentedControl selectedSegmentIndex]]
                        forParameter:searchString];
        [searchBar resignFirstResponder];
    }
}

#pragma mark - Helper Methods

/**
 *  Helper method to make an HTTP request to the server
 *
 *  @param type  request type, can be Title or ISBN
 *  @param param the parameters to give the http request
 */
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
    if (responseData == nil) {
        [searchResults removeAllObjects];
        [_tableView reloadData];
        UIAlertController *alertController = [UIAlertController alertControllerWithTitle:@"No search results found" message:@"Please run your search again." preferredStyle:UIAlertControllerStyleAlert];
        UIAlertAction *alertAction = [UIAlertAction actionWithTitle:@"Ok" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
            
        }];
        [alertController addAction:alertAction];
        [self presentViewController:alertController animated:YES completion:nil];
    }else{
        [self parseServerResponse:responseStr];
    }
    
}

/**
 *  Helper method to parse the response obtained from the server and add them to the search results array
 *
 *  @param responseStr the reponse to parse
 */
- (void)parseServerResponse:(NSString *)responseStr{
    NSArray<NSString *> *responseArray = [responseStr componentsSeparatedByString:@"\n"];
    for (NSUInteger i=0; i<responseArray.count; i++) {
        [searchResults addObject:responseArray[i]];
    }
    [_tableView reloadData];
}

#pragma mark - UITableViewDataSource

/**
 *  Datasource delegate for tableview
 *
 *  @param tableView tableview pointer
 *  @param section   the number of rows in a section
 *
 *  @return the number of rows to be added to the tableview
 */
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return [searchResults count];
}

/**
 *  Datasource delegate for tableview
 *
 *  @param tableView table view pointer
 *  @param indexPath index path pointer
 *
 *  @return <#return value description#>
 */
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell *cell = [_tableView dequeueReusableCellWithIdentifier:@"result"];
    if (cell == nil) {
        cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"result"];
    }
    FindBookParser *locParser = [[FindBookParser alloc]initWithServerResponse:[searchResults objectAtIndex:indexPath.row]];
    cell.textLabel.text = locParser.bookTitle;
    
    //Make UILabel for price
    UILabel *priceLabel = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, 100, 100)];
    priceLabel.text = locParser.price;
    cell.accessoryView = priceLabel;
    
    return cell;
}

#pragma mark - UITableViewDelegate

/**
 *  Delegate method for cell selection on a tableview cell
 *
 *  @param tableView the tableview pointer
 *  @param indexPath the index path pointer
 */
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    NSString *selectedItem = [searchResults objectAtIndex:indexPath.row];
    fbParser = [[FindBookParser alloc]initWithServerResponse:selectedItem];
    
    UIAlertController *alertController = [UIAlertController alertControllerWithTitle:@"Purchase book?" message:@"Confirm that you would like to purchase this book." preferredStyle:UIAlertControllerStyleAlert];
    
    UIAlertAction *alertActionPurchase = [UIAlertAction actionWithTitle:@"Yes" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
       //make HTTP Request that removes book.
        NSURLComponents *components = [NSURLComponents componentsWithString:@"http://localhost:9999/a"];
        NSURLQueryItem *reqType = [NSURLQueryItem queryItemWithName:@"requesttype" value:@"sellbook"];
        
        NSURLQueryItem *isbn = [NSURLQueryItem queryItemWithName:@"isbn" value:fbParser.isbn];
        NSURLQueryItem *title = [NSURLQueryItem queryItemWithName:@"title" value:fbParser.bookTitle];
        NSURLQueryItem *userId = [NSURLQueryItem queryItemWithName:@"id" value:fbParser.sjsuId];
        
        
        components.queryItems = @[reqType,isbn,title,userId];
        
        NSURL *url = components.URL;
        NSURLRequest *request = [NSURLRequest requestWithURL:url];
        NSURLResponse *response;
        NSError *error;
        
        NSData *responseData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
        
        NSString *responseStr = [[NSString alloc] initWithData:responseData encoding:NSUTF8StringEncoding];
        NSLog(@"%@",responseStr);

    }];
    
    UIAlertAction *alertActionCancel = [UIAlertAction actionWithTitle:@"No" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        
    }];
    
    [alertController addAction:alertActionPurchase];
    [alertController addAction:alertActionCancel];
    [self presentViewController:alertController animated:YES completion:^{
        double delayInSeconds = 2.0;
        dispatch_time_t popTime = dispatch_time(DISPATCH_TIME_NOW, delayInSeconds * NSEC_PER_SEC);
        dispatch_after(popTime, dispatch_get_main_queue(), ^(void){
            //code to be executed on the main queue after delay
            [self showSellerAlert:fbParser.sjsuId];
        });
    }];
    
    
}

/**
 *  Shows a UIAlertView with the seller information
 *
 *  @param sellerId the sjsu id of the seller
 */
-(void)showSellerAlert:(NSString *)sellerId{
    NSString *sellerName = @"";
    //make HTTP Request that displays seller info.
    NSURLComponents *components = [NSURLComponents componentsWithString:@"http://localhost:9999/a"];
    NSURLQueryItem *reqType = [NSURLQueryItem queryItemWithName:@"requesttype" value:@"getuser"];
    
    NSURLQueryItem *userId = [NSURLQueryItem queryItemWithName:@"id" value:sellerId];
    
    components.queryItems = @[reqType,userId];
    
    NSURL *url = components.URL;
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    NSURLResponse *response;
    NSError *error;
    
    NSData *responseData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
    
    sellerName = [[NSString alloc] initWithData:responseData encoding:NSUTF8StringEncoding];
    
    UIAlertController *displaySellerInfoAlertContoller = [UIAlertController alertControllerWithTitle:@"Your book can be found from this user:" message:sellerName preferredStyle:UIAlertControllerStyleAlert];
    UIAlertAction *displayeSellerInfoAction = [UIAlertAction actionWithTitle:@"Ok" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        [searchResults removeAllObjects];
        dispatch_async(dispatch_get_main_queue(), ^{
            [_tableView reloadData];
        });
    }];
    [displaySellerInfoAlertContoller addAction:displayeSellerInfoAction];
    [self presentViewController:displaySellerInfoAlertContoller animated:YES completion:nil];
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
