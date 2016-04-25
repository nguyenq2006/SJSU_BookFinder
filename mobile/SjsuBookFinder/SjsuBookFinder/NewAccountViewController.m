//
//  NewAccountViewController.m
//  SjsuBookFinder
//
//  Created by Ravin Sardal on 3/14/16.
//  Copyright © 2016 Ravin Sardal. All rights reserved.
//

#import "NewAccountViewController.h"
#import "Person.h"

@interface NewAccountViewController ()
@property (strong, nonatomic) IBOutlet UITextField *firstNameTextField;
@property (strong, nonatomic) IBOutlet UITextField *lastNameTextField;
@property (strong, nonatomic) IBOutlet UITextField *sjsuIdTextField;
@property (strong, nonatomic) IBOutlet UITextField *passwordTextField;

@end

@implementation NewAccountViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)createAccountButtonPressed:(UIButton *)sender {
    Person *p = [self createPersonFromUserInput];
    
    NSURLComponents *components = [NSURLComponents componentsWithString:@"http://localhost:9999/a"];
    NSURLQueryItem *reqType = [NSURLQueryItem queryItemWithName:@"requesttype" value:@"newuser"];
    
    NSURLQueryItem *firstname = [NSURLQueryItem queryItemWithName:@"firstname" value:p.firstName];
    NSURLQueryItem *lastname = [NSURLQueryItem queryItemWithName:@"lastname" value:p.lastName];
    NSURLQueryItem *userId = [NSURLQueryItem queryItemWithName:@"id" value:p.sjsuID];
    
    components.queryItems = @[reqType,firstname,lastname,userId];
    NSURL *url = components.URL;
    
//    __block NSString *dataStr = @"";
//    NSURLSession *session = [NSURLSession sharedSession];
//    [[session dataTaskWithURL:[NSURL URLWithString:url.absoluteString]
//            completionHandler:^(NSData *data,
//                                NSURLResponse *response,
//                                NSError *error) {
//                dataStr = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
//                
//            }] resume];
//    NSLog(@"%@",dataStr);
    //

    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    NSURLResponse *response;
    NSError *error;

    NSData *responseData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
    
    NSString *responseStr = [[NSString alloc] initWithData:responseData encoding:NSUTF8StringEncoding];

    
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    [defaults setObject:[p stringify] forKey:p.sjsuID];
    
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MainStoryboard" bundle:nil];
    NewAccountViewController *vc = (NewAccountViewController *)[storyboard instantiateViewControllerWithIdentifier:@"TabBarViewController"];
    [self presentViewController:vc animated:YES completion:nil];
}

-(Person *)createPersonFromUserInput{
    Person *p = [[Person alloc]initWithFirstName:self.firstNameTextField.text withLastName:self.lastNameTextField.text withSjsuId:self.sjsuIdTextField.text withPassword:self.passwordTextField.text];
    return p;
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
