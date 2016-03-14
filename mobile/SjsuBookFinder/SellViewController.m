//
//  SellViewController.m
//  SjsuBookFinder
//
//  Created by Mohnish kadakia on 3/12/16.
//  Copyright © 2016 Ravin Sardal. All rights reserved.
//

#import "SellViewController.h"

@interface SellViewController ()

@end

@implementation SellViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self performSegueWithIdentifier:@"showLogin" sender:self];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
#warning Incomplete implementation, return the number of sections
    return 0;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
#warning Incomplete implementation, return the number of rows
    return 0;
}



@end
