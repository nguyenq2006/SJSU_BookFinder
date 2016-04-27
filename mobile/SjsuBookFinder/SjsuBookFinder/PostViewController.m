//
//  PostViewController.m
//  SjsuBookFinder
//
//  Created by Ravin Sardal on 4/25/16.
//  Copyright © 2016 Ravin Sardal. All rights reserved.
//

#import "PostViewController.h"

@interface PostViewController ()<UITextFieldDelegate>
@property (strong, nonatomic) IBOutlet UITextField *isbnTextField;

@property (strong, nonatomic) IBOutlet UITextField *titleTextField;
@property (strong, nonatomic) IBOutlet UITextField *authorTextField;
@property (strong, nonatomic) IBOutlet UITextField *priceTextField;
@property (strong, nonatomic) IBOutlet UISwitch *hardCoverSwitch;
@property (strong, nonatomic) IBOutlet UIButton *postButton;

@end

@implementation PostViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.navigationItem.title = @"Post";
    
    self.priceTextField.delegate = self;
    
    self.postButton.layer.cornerRadius = 10;
    self.postButton.clipsToBounds = YES;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (IBAction)postButtonPressed:(UIButton *)sender {
    
    //http://localhost:9999/a?requesttype=addbook&isbn=91919191911&title=DataStructAndAlgo&author=Horstmann&price=20&id=009558549&hardcover=true
    
    NSURLComponents *components = [NSURLComponents componentsWithString:@"http://localhost:9999/a"];
    NSURLQueryItem *reqType = [NSURLQueryItem queryItemWithName:@"requesttype" value:@"addbook"];
    
    NSURLQueryItem *isbn = [NSURLQueryItem queryItemWithName:@"isbn" value:_isbnTextField.text];
    NSURLQueryItem *title = [NSURLQueryItem queryItemWithName:@"title" value:_titleTextField.text];
    NSURLQueryItem *author = [NSURLQueryItem queryItemWithName:@"author" value:_authorTextField.text];
    NSURLQueryItem *price = [NSURLQueryItem queryItemWithName:@"price" value:_priceTextField.text];
    NSURLQueryItem *userId = [NSURLQueryItem queryItemWithName:@"id" value:@"009558549"];
    NSURLQueryItem *hardcover = [NSURLQueryItem queryItemWithName:@"hardcover" value:@"true"];
    
    
    components.queryItems = @[reqType,isbn,title,author,price,userId,hardcover];
    
    NSURL *url = components.URL;
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    NSURLResponse *response;
    NSError *error;
    
    NSData *responseData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
    
    NSString *responseStr = [[NSString alloc] initWithData:responseData encoding:NSUTF8StringEncoding];
    NSLog(@"%@",responseStr);
}

#pragma mark - UITextFieldDelegate

-(BOOL) textFieldShouldReturn:(UITextField *)textField{
    [textField resignFirstResponder];
    return YES;
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
