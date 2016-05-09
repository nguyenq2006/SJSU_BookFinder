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

/**
 *  Controller to manager the login view
 */
@interface LoginViewController ()<UITextFieldDelegate>
@property (strong, nonatomic) IBOutlet UITextField *sjsuIdTextField;
@property (strong, nonatomic) IBOutlet UITextField *passwordTextField;

@end

@implementation LoginViewController

/**
 *  Do any additional setup after loading the view.
 */
- (void)viewDidLoad {
    [super viewDidLoad];
    self.passwordTextField.delegate = self;
}

/**
 *  Dispose of any resources that can be recreated.
 */
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

/**
 *  Interface builder handler to handle login button press
 *
 *  @param sender button pointer
 */
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

/**
 *  Interface builder handler to handle signup button press
 *
 *  @param sender button pointer
 */
- (IBAction)signUpButtonPressed:(UIButton *)sender {
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MainStoryboard" bundle:nil];
    NewAccountViewController *vc = (NewAccountViewController *)[storyboard instantiateViewControllerWithIdentifier:@"NewAccountViewController"];
    [self presentViewController:vc animated:YES completion:nil];
}

#pragma mark - UITextFieldDelegate

/**
 *  Delegate method for responding to the return key press
 *
 *  @param textField text field pointer
 *
 *  @return the bool indicating whether the text field should return
 */
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
