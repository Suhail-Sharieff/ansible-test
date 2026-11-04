variable region{}
variable instance_type{}
variable ami{}
variable user{}
provider "aws" {
    region = var.region
    alias = "my-aws-provider"
}
resource "aws_security_group" "allow-ssh" {
  provider = aws.my-aws-provider
  region = var.region 
  ingress{
    cidr_blocks = [ "0.0.0.0/0" ]
    from_port = "22"
    to_port = "22"
    protocol = "tcp"
  }
  ingress{
    cidr_blocks = [ "0.0.0.0/0" ]
    from_port = "443"
    to_port = "443"
    protocol = "tcp"
  }
  egress{
    cidr_blocks = [ "0.0.0.0/0" ]
    from_port = 0
    to_port = 0
    protocol = "-1"
  }
  ingress{
    cidr_blocks = [ "0.0.0.0/0" ]
    from_port = "80"
    to_port = "80"
    protocol = "tcp"
  }
}
resource "aws_instance" "web01" {
  region = var.region
  instance_type = var.instance_type
  ami = var.ami
  provider = aws.my-aws-provider
  vpc_security_group_ids = [ aws_security_group.allow-ssh.id ]
  key_name = "suhai_aws_key"
  provisioner "remote-exec" {
    connection {
        host = self.public_ip
        user = var.user
        private_key = file("./suhai_aws_key.pem")
    }
    inline = [ 
        "sudo apt update",
        "sudo apt install apache2 -y",
        "sudo systemctl restart apache2",
        "sudo systemctl restart apache2"
    ]
  }
}
output "print_ip" {
    description = "ip addr of launched instance"
  value = aws_instance.web01.public_ip
}

