//
//  ViewController.m
//  SjsuBookFinder
//
//  Created by Ravin Sardal on 3/2/16.
//  Copyright © 2016 Ravin Sardal. All rights reserved.
//

#import "ViewController.h"
/**
 *  Root View Controller
 */
@interface ViewController ()

@end

@implementation ViewController

/**
 *  Do any additional setup after loading the view, typically from a nib
 */
- (void)viewDidLoad {
    [super viewDidLoad];
}

/**
 *  Override viewDidAppear
 *
 *  @param animated appear animation boolean
 */
-(void)viewDidAppear:(BOOL)animated{
    [super viewDidAppear:animated];
    
}

/**
 *  Interface builder button press handler
 *
 *  @param sender button pointer
 */
- (IBAction)buttonPressed:(UIButton *)sender {
    NSURLSession *session = [NSURLSession sharedSession];
    [[session dataTaskWithURL:[NSURL URLWithString:@"http://localhost:9999/test?name=john&bookid=13784794535"]
            completionHandler:^(NSData *data,
                                NSURLResponse *response,
                                NSError *error) {
                NSString *dataStr = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
                NSLog(@"%@",dataStr);
            }] resume];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
