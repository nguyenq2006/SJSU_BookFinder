//
//  LoginViewController.m
//  SjsuBookFinder
//
//  Created by Ravin Sardal on 3/14/16.
//  Copyright © 2016 Ravin Sardal. All rights reserved.
//

#import "LoginViewController.h"
#import "NewAccountViewController.h"
#import "Person.h"

@interface LoginViewController ()<UITextFieldDelegate>
@property (strong, nonatomic) IBOutlet UITextField *sjsuIdTextField;
@property (strong, nonatomic) IBOutlet UITextField *passwordTextField;

@end

@implementation LoginViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.passwordTextField.delegate = self;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (IBAction)loginButtonPressed:(UIButton *)sender {
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    Person *p = [Person unstringify:[defaults objectForKey:self.sjsuIdTextField.text]];
    if ([p.password isEqualToString:self.passwordTextField.text]) {
        UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MainStoryboard" bundle:nil];
        NewAccountViewController *vc = (NewAccountViewController *)[storyboard instantiateViewControllerWithIdentifier:@"TabBarViewController"];
        [defaults setObject:p.sjsuID forKey:@"sessionUserId"];
        [self presentViewController:vc animated:YES completion:nil];
    }
}

- (IBAction)signUpButtonPressed:(UIButton *)sender {
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MainStoryboard" bundle:nil];
    NewAccountViewController *vc = (NewAccountViewController *)[storyboard instantiateViewControllerWithIdentifier:@"NewAccountViewController"];
    [self presentViewController:vc animated:YES completion:nil];
}

#pragma mark - UITextFieldDelegate
- (BOOL)textFieldShouldReturn:(UITextField *)textField{
    [self loginButtonPressed:nil];
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
