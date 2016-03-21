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
