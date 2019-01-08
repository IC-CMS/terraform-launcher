provider "consul" {
  address 	= "127.0.0.1:8500"
  version	= "2.1.0"
}

provider "vault" {
  address	= "http://127.0.0.1:8200"
  token		= "873773ca-91ae-3573-f04f-2576c4af3f31"
  version	= "1.1.0"
}

resource "consul_service" "jenkins" {
  name    = "jenkins"
  node    = "${consul_node.compute.name}"
  port    = 80
  tags    = ["tag0"]

}

resource "consul_node" "compute" {
  name    = "jenkins"
  address = "localhost"

}
